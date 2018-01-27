import {Injectable} from '@angular/core';
import {BackendService} from './backend.service';
import {Group} from "../models/group";
import {User} from "../models/User";

@Injectable()
export class GroupService {
  groupLimitPerUser: number = 4;
  groups: Group[];
  maxMemberPerGroup: number = 8;

  constructor(private backendService: BackendService) {
  }

  createGroup(groupName: string, creatorPhone: number) {
    return this.backendService.registerNewPot(groupName, creatorPhone);
  }

  setGroupName(groupId: string, newName: string) {
    return this.backendService.setGroupName(groupId, newName);
  }

  getPotMoneyInfoPerUser(){
    return this.backendService.getInvestmentsById(localStorage.getItem('telephone'));
  }

  invest(userPhone, groupId, investment) {
    return this.backendService.invest(userPhone, groupId, investment);
  }

  borrowMoney(userPhone: string, potId: string, amount: string) {
    return this.backendService.registerNewLoan(userPhone, potId, amount);
  }

  sendInvitation(groupId: string, invited: string) {
    return this.backendService.sendInvitationToUser(groupId, invited);
  }

  getLoans(potId: number) {
    return this.backendService.getLoansByUserId(localStorage.getItem('telephone'));
  }

  payBackLoan(loanId: string) {
    return this.backendService.paybackLoan(loanId);
  }

  leavePot(potId: string) {
    return this.backendService.leavePot(localStorage.getItem('telephone'), potId);
  }
}
