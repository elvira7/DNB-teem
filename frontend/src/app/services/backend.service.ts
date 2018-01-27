import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Observable } from "rxjs/Observable";
import {User} from "../models/user";

@Injectable()
export class BackendService {
    private backendLocation: string = 'http://localhost';
    private backendPort: string = '8080';
    private backendUrl: string = this.buildBaseUrl();

    private token: string;

    constructor(private http: Http) { }

    private buildBaseUrl(): string {
        return `${this.backendLocation}:${this.backendPort}`;
    }

    private buildHttpRequest(URL, headers, body){}

    login(userPhone:string, userPass:string) {
      let requestUrl= this.backendUrl + '/user/login';
      let headers = new Headers(
        { 'Content-Type': 'application/json' }
      );
      let jsonBody = { 'telephone': userPhone, 'password': userPass};
      return this.http.post(requestUrl, jsonBody, { headers: headers });
    }

    registerUSer(user: User) {
      let requestUrl = this.backendUrl + '/user/register';
      let headers = new Headers({ 'Content-Type': 'application/json' });
      let jsonBody = JSON.stringify(user);

      console.log('Json body = ' + user);
      return this.http.post(requestUrl, jsonBody , { headers: headers });
    }


  findByTelephone(telephone: number): Observable<Response> {
        let requestUrl = this.backendUrl + '/rest/user/telephone';
        let headers = new Headers(
            {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            });
        return this.http.post(requestUrl, telephone, { headers: headers });
    }

    registerNewPot(groupName: string, telephone: number): Observable<Response> {
        let requestUrl = this.backendUrl + '/pot/register';
        let headers = new Headers(
            { 'Content-Type': 'application/json' }
        );
        console.log('in backendService.registerNewPot:');
        console.log('name: "' + groupName + '", telephone: "' + telephone + '"')
        let jsonBody = { 'name': groupName, 'telephone': telephone };
        console.log(jsonBody);
        return this.http.post(requestUrl, jsonBody, { headers: headers });
    }

  getPotsByTelephone(userPhone: number): Observable<Response> {
        console.log('Retrieving groups for user ' +
            localStorage.getItem('firstName') + ' ' +
            localStorage.getItem('lastName'));

        let requestUrl = this.backendUrl + '/rest/user/potlist';
        let headers = new Headers({
            'Content-Type': 'application/json'
        });
        return this.http.post(requestUrl, userPhone, { headers: headers });
    }

    setGroupName(groupId: string, newGroupName: string) {
        let requestGroupUrl = this.backendUrl + '/rest/pot/id/name';
        let queryHeaders = new Headers({
            'Content-Type': 'application/json'
        });
        let jsonBody = { 'potId': groupId, 'name': newGroupName };
        return this.http.post(requestGroupUrl, jsonBody, { headers: queryHeaders });
    }

    getMemberListForGroup(groupId: number){
      let requestUrl = this.backendUrl + '/rest/pot/userlist';
      let queryHeaders = new Headers({
        'Content-Type': 'application/json'
      });
      let body = groupId;
      return this.http.post(requestUrl, body, { headers: queryHeaders });
    }


  sendInvitationToUser(groupId: string, invitedId: string) {
        let requestUrl = this.backendUrl + '/user/invitation/send';
        let queryHeaders = new Headers({
            'Content-Type': 'application/json'
        });
        let body = { 'potId': groupId, 'invite': invitedId };
        return this.http.post(requestUrl, body, { headers: queryHeaders });
    }

  getInviteList(userPhone: number) {
        let requestUrl = this.backendUrl + '/user/invitation/all';
        let queryHeaders = new Headers({
            'Content-Type': 'application/json'
        });
        let body = userPhone;
        return this.http.post(requestUrl, body, { headers: queryHeaders });
    }

    invest(userPhone: string, groupId: string, investment: string){
        let requestUrl = this.backendUrl + '/user/investment/invest';
        let queryHeaders = new Headers({
            'Content-Type': 'application/json'
        });
        let body = {"telephone": userPhone, "potId": groupId,"investment": investment};
        return this.http.post(requestUrl, body, { headers: queryHeaders });
    }


    getPendingIncome(userPhone: string){
      let requestUrl = this.backendUrl + '/user/investment/pendingincome';
      let queryHeaders = new Headers({
        'Content-Type': 'application/json'
      });
      let body = {"telephone": userPhone};
      return this.http.post(requestUrl, body, { headers: queryHeaders });
    }

  getTotalInvestmentById(userPhone: string){
    let requestUrl = this.backendUrl + '/user/totalinvestment';
    let queryHeaders = new Headers({
      'Content-Type': 'application/json'
    });
    let body = {"telephone": userPhone};
    return this.http.post(requestUrl, body, { headers: queryHeaders });
  }

  getInvestmentsById(userPhone: string){
    let requestUrl = this.backendUrl + '/user/investments';
    let queryHeaders = new Headers({
      'Content-Type': 'application/json'
    });
    let body = {"telephone": userPhone};
    return this.http.post(requestUrl, body, { headers: queryHeaders });
  }

  registerNewLoan(userPhone:string, potId: string, amount:string){
    let requestUrl= this.backendUrl + '/loan/register';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    let jsonBody = { 'telephone': userPhone, 'potId': potId, 'loan': amount};
    return this.http.post(requestUrl, jsonBody, { headers: headers });
  }

  getLoansByUserId(userPhone: string){
    let requestUrl= this.backendUrl + '/loan/getloans';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    let jsonBody = { 'telephone': userPhone};
    return this.http.post(requestUrl, jsonBody, { headers: headers });
  }
  paybackLoan(loanId: string){
    let requestUrl= this.backendUrl + '/loan/payback';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    let jsonBody = { 'loanId': loanId};
    return this.http.post(requestUrl, jsonBody, { headers: headers });
  }

  getPotPercentage(userPhone: string, potId: string){
    let requestUrl= this.backendUrl + '/pot/getpotpart';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    let jsonBody = {"telephone":userPhone, "potId": potId};
    return this.http.post(requestUrl, jsonBody, { headers: headers });
  }

  findByPotId(potId: number){
    let requestUrl= this.backendUrl + '/rest/pot/id';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    return this.http.post(requestUrl, potId, { headers: headers });
  }

  getUserPot(userPhone: number, potId: number){
    let requestUrl= this.backendUrl + '/rest/pot/userpot';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    let body = {"userId": userPhone, "potId": potId};
    return this.http.post(requestUrl, body, { headers: headers });
  }

  acceptInvitation(userPhone: string, potId: string){
    let requestUrl= this.backendUrl + '/user/invitation/accept';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    let body = {"telephone": userPhone, "potId": potId};
    return this.http.post(requestUrl, body, { headers: headers });
  }

  declineInvitation(userPhone: string, potId: string){
    let requestUrl= this.backendUrl + '/user/invitation/decline';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    let body = {"telephone": userPhone, "potId": potId};
    return this.http.post(requestUrl, body, { headers: headers });
  }

  leavePot(userPhone: string, potId: string){
    let requestUrl= this.backendUrl + '/pot/leave';
    let headers = new Headers(
      { 'Content-Type': 'application/json' }
    );
    let body = {"telephone": userPhone, "potId": potId};
    return this.http.post(requestUrl, body, { headers: headers });
  }

}
