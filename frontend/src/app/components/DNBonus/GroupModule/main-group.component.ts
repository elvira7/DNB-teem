import {Component, OnInit, OnDestroy} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../../services/user.service';
import {Group} from '../../../models/group';
import {GroupService} from '../../../services/group.service';
import {NotificationBarService, NotificationType} from 'angular2-notification-bar';


@Component({
  selector: 'main-group',
  templateUrl: 'main-group.component.html',
  styleUrls: ['main-group.component.css'],
  host: {'class': 'transparent-background'},
  providers: [GroupService]
})

export class MainGroupComponent implements OnInit, OnDestroy {
  private _groups: Group[] = [];
  get groups(): Group[] {
    return this.userService.user.groups;
  }

  set groups(groups: Group[]) {
    //this._groups = groups;
    this.userService.user.groups = groups;
  }

  inviteNodeClicked: boolean = false;

  get _inviteNodeClicked(): boolean {
    return this.inviteNodeClicked;
  }

  set _inviteNodeClicked(_value: boolean) {
    let value = _value && this._invitations.length > 0;
    this._invitation_img_URL = value ?
      'resources/icons/invitations_black.png' :
      'resources/icons/invitations_white.png';
    this._invitation_fill = value ?
      'fill: #ffffff' :
      'fill: #0e3944';
    this.inviteNodeClicked = value;
  }
  _inviteConfirm_img_url:string = 'resources/icons/check_white.png';
  _invitation_img_URL: string = 'resources/icons/invitations_white.png';
  _invitation_fill: string = 'fill: #0e3944';

  _invitations: {
    groupId: number,
    groupName: string
  }[];

    get invitationList(){
      return this._invitations.slice(0,4);
    }

  inviteList_base_Y: number = 40;


  constructor(private userService: UserService,
              private groupService: GroupService,
              private router: Router,
              public notificationBarService: NotificationBarService) {

  }

  ngOnInit(): void {
    console.log('MainGroup onInit called...');
    if (!this.userService.user.groups)
      this.userService.updateUserGroups();
    //this.groups = this.userService.user.groups;
    this.getInvitations(+localStorage.getItem('telephone'));
  }

  ngOnDestroy() {
    this._inviteNodeClicked = false;
  }

  private clamp(value, min, max) {
    if (value < min) return min;
    if (value > max) return max;
    return value;
  }

  clampText(groupName: string, limit: number) {
    return groupName.length > limit ? groupName.substr(0, limit - 1).concat('...') : groupName;
  }

  getGroupRadius(group: Group): number {
    let userCount: number;
    if (!group || !group.users) {
      userCount = 0;
    } else {
      userCount = group.users.length;
    }
    return this.clamp(userCount * 5, 40, 60);
  }

  getFontSize(group: Group): string {
    return Math.min(this.getGroupRadius(group) / 2 - 2, 14) + 'px';
  }

  showGroupDetails(id: number) {
    this.router.navigate(['group-details', id]);
  }

  createNewGroup() {
    if (this.hasReachedGroupLimit()) {
      this.notificationBarService.create({
        message: 'You have reached the maximum TEEM limit',
        type: NotificationType.Warning
      });
    } else {
      this.router.navigate(['create-group']);
    }
  }

  getInvitations(userPhone: number) {
    this._invitations = [];
    this.userService.getInvitations(userPhone).subscribe(
      (invitations) => {
        invitations.json().forEach(
          (invite) => {
            this._invitations.push({groupId: invite[0], groupName: invite[3]});
          }
        );
      },
      () => {
      }
    );
  }

  acceptInvite(groupId: string) {
    if (this.hasReachedGroupLimit()) {
      this.notificationBarService.create({
        message: 'You have reached the maximum TEEM limit',
        type: NotificationType.Warning
      });
      return;
    }

    let groupName: string = this._invitations.find(i => i.groupId == +groupId).groupName;
    console.log('ACCEPTED: Invitation to group ' + groupName);
    this.userService.acceptInvitation(localStorage.getItem('telephone'), groupId)
      .subscribe(
        (confirmation) => {
          this._invitations = this._invitations.filter(invite => invite.groupId != +groupId);
          this.userService.updateUserGroups();
          this.notificationBarService.create({
            message: 'Welcome to '+ groupName + '!',
            type: NotificationType.Success
          });
        },
        (error) => {
          console.log('ACCEPT error:');
          console.log(error);
        }
      );

  }

  declineInvite(groupId: string) {
    let groupName = this._invitations.find(i => i.groupId == +groupId).groupName;
    console.log( 'DECLINED: Invitation to group ' + groupName);
    this.userService.declineInvitation(localStorage.getItem('telephone'), groupId)
      .subscribe(
        (confirmation) => {
          console.log('DECLINED confirmation:');
          console.log(confirmation);
          this.notificationBarService.create({
            message: 'Invitation to join TEEM ' + groupName + ' declined',
            type: NotificationType.Info
          });
          this._invitations = this._invitations.filter(invite => invite.groupId != +groupId);
          this.userService.updateUserGroups();
        },
        (error) => {
          console.log('DECLINED error:');
          console.log(error);
        }
      );

  }

  trackInvites(index, item) {
    return item.groupId;
  }

  hasReachedGroupLimit(): boolean {
    return this.groups.length >= this.groupService.groupLimitPerUser;
  }

  y_inviteListOffset(index: number) {
    let offset = 10;
    return (this.inviteList_base_Y + offset * index);
  }

  x_comp(index: number) {
    return 70 + 15 * Math.sin(Math.PI / (this.groups.length + 1) * index);
  }

  y_comp(index: number) {
    return 15 + ((90 - 15) / this.groups.length + 1) * index + 1;
  }

}

