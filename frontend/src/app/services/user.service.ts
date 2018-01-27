import { Injectable } from "@angular/core";
import { Http, Headers } from '@angular/http';

import { BackendService } from './backend.service';
import { User } from '../models/user';
import { Group } from "../models/group";

@Injectable()
export class UserService {
  user: User;

  constructor(
    private http: Http,
    private backendService: BackendService
    ) { }

  getUser(telephone: number) {
    return this.backendService.findByTelephone(telephone);
  }

  updateUser(){
    this.getUser(+localStorage.getItem('telephone')).subscribe(
      (user) =>{
        this.user = user.json();
        for (let entry in this.user) {
          localStorage.setItem(entry, this.user[entry]);
        }
        this.getGroups().subscribe(
          (groups)=> this.user.groups = groups.json()
        );
        console.log('user info updated');},
      ()=>{
        console.log('error updating user info');
      }
    )
  }

  getGroups() {
    return this.backendService.getPotsByTelephone(+localStorage.getItem('telephone'));
  }

  updateUserGroups() {
    this.getGroups().subscribe(
      groups => {
        this.user.groups = groups.json();
      },
      error => {
        console.log('Error getting groups');
        this.user.groups = [];
      });
  }

  setGroupName(groupId: string, newName: string) {
    return this.backendService.setGroupName(groupId, newName);
  }

  getInvitations(userPhone : number){
    return this.backendService.getInviteList(userPhone);
  }

  getMemberList(groupId: number){
    return this.backendService.getMemberListForGroup(groupId);
  }

  acceptInvitation(userPhone: string, potId: string){
    return this.backendService.acceptInvitation(userPhone, potId);
  }

  declineInvitation(userPhone: string, potId: string){
    return this.backendService.declineInvitation(userPhone, potId);
  }

}
