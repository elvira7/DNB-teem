import {Component, OnInit} from '@angular/core';
import {Group} from '../../../models/group';
import {UserService} from '../../../services/user.service';
import {Router, ActivatedRoute, Params} from '@angular/router';

import {NotificationBarService, NotificationType} from 'angular2-notification-bar';
import {User} from "../../../models/user";
import {BackendService} from "../../../services/backend.service";
import {GroupService} from "../../../services/group.service";
import {Loan} from "../../../models/loan";


@Component({
  selector: 'group-details',
  templateUrl: './group-details.component.html',
  styleUrls: ['./group-details.component.css'],
  host: {'class': 'transparent-background'}
})

export class GroupDetailsComponent implements OnInit {
  get availableMoney(){ return Math.round(this.selectedGroup.availableMoney * 100) / 100;}
  set availableMoney(value){ this.selectedGroup.availableMoney = value; }
  get totalMoney(){ return Math.round(this.selectedGroup.totalMoney * 100) / 100;}
  set totalMoney(value){this.selectedGroup.totalMoney = value; }

  _loans: Loan[] = [];
  _members: User[] = [];
  selectedGroup: Group;
  invitedUser: string;
  overlayVisible: boolean;

  userPot: {
    invested: number
    potPart: number
    interests: number
  } = {
    invested: 0,
    potPart: 0,
    interests: 0
  };

  get invested(){ return Math.round(this.userPot.invested * 100) / 100;}
  get interests(){ return Math.round(this.userPot.interests * 100) / 100;}
  get paybackNodeOutlineColour(){ return this._loans.length > 0 ? '#f06d00':'white';}

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService,
              private groupService: GroupService,
              public notificationBarService: NotificationBarService,
              private backendService: BackendService) {
  }

  ngOnInit(): void {
    let id = +this.route.snapshot.params['id'];
    this.selectedGroup = this.userService.user.groups[id];
    console.log(this.selectedGroup);
    this.getMembers();
    this.getUserPot();
    this.getInterests();
    this.getLoansForGroup();
  }

  private updateUserPot(responseObject) {
    this.userPot.invested = responseObject.invested;
    this.userPot.potPart = responseObject.pot.totalMoney == 0 ?
                            0 :
                            Math.round(((responseObject.invested / responseObject.pot.totalMoney) * 100)*100) / 100;
  }

  private getUserPot() {
    this.backendService.getUserPot(this.userService.user.telephone, this.selectedGroup.potId)
      .subscribe(
        data => {
          let responseObject = data.json();
          this.updateUserPot(responseObject);
        },
        error => {
          console.error('Failed to fetch Pot Percentage')
        }
      );
  }

  private getInterests() {
    this.groupService.getPotMoneyInfoPerUser().subscribe(
      response => {
        console.log('interest per group:');
        console.log(response.json());
        this.userPot.interests = response.json().find(
          object =>
            object.pot.potId == this.selectedGroup.potId
          ).interest;
      },
      error => {
        console.error('Could not fetch interest from user_pot');
      }
    );
  }

  isEmpty(field): boolean {
    let isEmpty: boolean = (field == null || field == '');
    if (isEmpty) {
      this.notificationBarService.create({
        message: 'Input is empty',
        type: NotificationType.Warning
      });
      return isEmpty;
    }
  }

  isNotValidAmount(_field): boolean {
    let field = ''+ (+_field);
    let _isNotInteger = !Number.isInteger(+field);
    if (_isNotInteger) {
      this.notificationBarService.create({
        message: 'Please enter whole numbers only',
        type: NotificationType.Warning
      });
      return _isNotInteger;
    } else if( +_field< 0){
      this.notificationBarService.create({
        message: 'Please enter positive numbers only',
        type: NotificationType.Warning
      });
      return +_field < 0;
    }
  }

  toggleOverlay(visible: boolean, cls?: string) {
    this.overlayVisible = visible;
    document.getElementById('dropdown-overlay').className = '';
    if (cls) {
      document.getElementById('dropdown-overlay').classList.add(cls);
      console.log('Toggle overlay:' + cls);
    }
  }

  leaveGroup() {
    if (this._loans.length > 0){
      this.notificationBarService.create({
        message: 'You still have pending loans',
        type: NotificationType.Warning
      });
      this.toggleOverlay(false);
      return;
    }

    let potId = String(this.selectedGroup.potId);
    this.groupService.leavePot(potId).subscribe(
      (confirmed)=>{
        console.log('Leaving group: ' + this.selectedGroup.name);
        this.userService.updateUserGroups();
        this.router.navigate(['main-group']);
      },
      (error)=>{
        console.log('Backend error :');
        console.log(error.json().message);
      }
    );
    // TODO: -> validate removing current user from group:
    // if user owes to the group (ServletException error from backend)
    //    show error message
    // else
    //    send message to backend
    //    update current user groups
  }

  checkGroupMemberLimit(){
    if(this._members.length >= this.groupService.maxMemberPerGroup){
      this.notificationBarService.create({
        message: 'Member limit reached',
        type: NotificationType.Warning
      });
    } else{
      this.toggleOverlay(true, 'add-member');
    }
  }

  inviteToGroup() {

    let groupNumber = `${this.selectedGroup.potId}`;
    let invitedUser = this.invitedUser;

    if (this.isEmpty(invitedUser) || this.isNotValidAmount(invitedUser))
      return;

    if(this.userService.user.telephone == +invitedUser){
      this.notificationBarService.create({
        message: 'You cannot invite yourself',
        type: NotificationType.Warning
      });
      return;
    }

    let existingUser: User = this._members.find(user => user.telephone == +invitedUser);
    if (existingUser) {
      this.notificationBarService.create({
        message: `User already in TEEM 
          (${existingUser.firstName} ${existingUser.lastName})`,
        type: NotificationType.Warning
      });
      return;
    }

    this.groupService.sendInvitation(groupNumber, invitedUser).subscribe(
      data => {
        this.notificationBarService.create({
          message: 'Invitation sent',
          type: NotificationType.Success,
        });
        this.invitedUser = '';
        this.toggleOverlay(false);
      },
      error => {
        let errorMsg = error.json().message;
        this.notificationBarService.create({
          message: errorMsg,
          type: NotificationType.Warning
        });
      }
    );
  }

  addMoney(amount: string) {
    if (this.isEmpty(amount) || this.isNotValidAmount(amount))
      return;

    this.groupService.invest(
      this.userService.user.telephone,
      this.selectedGroup.potId,
      String(+amount)
    ).subscribe(
      data => {

        let responseObject = data.json();
        this.updateUserPot(responseObject);

        this.selectedGroup.availableMoney = responseObject.pot.availableMoney;
        this.selectedGroup.totalMoney = responseObject.pot.totalMoney;
        console.log('Money added to the pot: ' + +amount);
        console.log('Total in the pot: ' + this.selectedGroup.availableMoney);

        this.notificationBarService.create({
          message: +amount + ',- added to the pot',
          type: NotificationType.Success
        });
        this.userService.updateUser();
        this.toggleOverlay(false);
      },
      error => {
        let parsedResponse = JSON.parse(error._body);
        let parsedError: string = parsedResponse.message;
        console.log(parsedResponse);
        this.notificationBarService.create({
          message: parsedError,
          type: NotificationType.Warning
        });
      },
      () => {
      }
    );
  }

  borrowMoney(amount: string) {
    if (this.isEmpty(amount) || this.isNotValidAmount(amount))
      return;

    if (this.selectedGroup.availableMoney < +amount) {
      this.notificationBarService.create({
        message: 'Only ' + this.availableMoney + ',- available',
        type: NotificationType.Info
      });
      return;
    }

    let userId: string = `${this.userService.user.telephone}`;
    let potId: string = `${this.selectedGroup.potId}`;
    this.groupService.borrowMoney(userId, potId, String(+amount)).subscribe(
      data => {
        this.selectedGroup.availableMoney -= +amount;
        this.notificationBarService.create({
          message: 'You borrowed ' + +amount+ ',-',
          type: NotificationType.Success
        });
        this.toggleOverlay(false);
      },
      error => {
        console.log('backendError: registerNewLoan()');
        this.notificationBarService.create({
          message: error.json().message,
          type: NotificationType.Error
        });
      },
      () => this.getLoansForGroup()
    );
  }

  getLoansForGroup(){
    this.groupService.getLoans(this.selectedGroup.potId).subscribe(
      data => {
        let loans: Loan[] = data.json();
        this._loans = loans
          .filter(
            (loan) =>
              loan.user.telephone == this.userService.user.telephone &&
              loan.pot.potId == this.selectedGroup.potId
        );
        this._loans.map( loan => {
          loan.dueDate = 3;
          loan.money = Math.round( (loan.money * 1.1) * 100) /100;
        });
        console.log('getloansForGroup DONE');
      }
    );
  }

  trackByLoan(index, item) {
    return item.loanId;
  }

  payBackLoan(_loanId: number){
    if(this.userService.user.money < this._loans.find(loan => loan.loanId == _loanId).money){
      this.notificationBarService.create({
        message: 'Not enough money to pay this loan back',
        type: NotificationType.Warning
      });
      return;
    }

    let loanId = ''+_loanId;
    this.groupService.payBackLoan(loanId).subscribe(
      data => {
        let paidBackLoan: Loan = this._loans.find(loan => loan.loanId == +loanId);
        this.notificationBarService.create({
          message: 'You paid back ' + paidBackLoan.money + ',-',
          type: NotificationType.Success
        });
        this._loans = this._loans.filter(loan => loan.loanId != paidBackLoan.loanId);
        this.selectedGroup.availableMoney += paidBackLoan.money;
        if(this._loans.length == 0)
          this.toggleOverlay(false);
      },
      error => {
        this.notificationBarService.create({
          message: error.json().message,
          type: NotificationType.Warning
        });
      }
    )

  }

  setGroupName(newName: string) {
    let reply;
    let groupId = `${this.selectedGroup.potId}`;
    this.userService.setGroupName(groupId, newName).subscribe(
      data => {
        reply = JSON.parse(JSON.stringify(data))._body;
      },
      error => {
        console.log('Connection error:')
        console.log(error);
      },
      () => {
        if (reply == '') {
          console.log('Group not found!');
        }
        else {
          reply = JSON.parse(reply);
          console.log(`Group name changed to ${reply.name}`);
          this.userService.updateUserGroups();
        }
      }
    );
  }

  getMembers() {
    this.userService.getMemberList(this.selectedGroup.potId).subscribe(
      data => {
        this._members = data.json().filter(
          (user:User) => user.telephone != this.userService.user.telephone
        );
      }
    );
  }

}
