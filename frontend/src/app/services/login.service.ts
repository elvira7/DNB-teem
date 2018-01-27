import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { BackendService } from './backend.service';

@Injectable()
export class LoginService {
  token: string;

  constructor(
    private http: Http,
    private backendService: BackendService
  ) { }

  getTokenForUser(userPhone: string, userPass: string) {
    return this.backendService.login(userPhone, userPass);
  }

  isLoggedIn(): boolean {
    let loginMsg = 'The user is logged ';
    let isLoggedIn = false;
    let userPhone = localStorage.getItem('telephone');
    let userToken = localStorage.getItem('token');
    if (
      (userPhone != null) &&
      (userPhone !== '') &&
      (userToken != null) &&
      (userToken !== '')
    ) {
      loginMsg += 'in.';
      isLoggedIn = true;
    } else {
      loginMsg += 'out.';
      isLoggedIn = false;
    }
    console.log(loginMsg);
    return isLoggedIn;
  }

}
