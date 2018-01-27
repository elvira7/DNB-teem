import { Component, OnInit } from '@angular/core';

import { LoginService } from '../services/login.service';
import { User } from '../models/user';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'login',
  templateUrl: 'login.component.html'
})


export class Login implements OnInit {

  private _loading: boolean = false;

  get loading(): boolean { return this._loading; }
  set loading(isloading: boolean) {
    console.log('loading = ' + isloading);
    this._loading = isloading;
  }
  errorMsg: string;
  error: boolean;

  private model = { 'telephone': '', 'password': '' };
  private user: User;
  private token: string;

  constructor(
    private loginService: LoginService,
    private userService: UserService,
    private router: Router
  ) {
    console.log('login constructor called...');
  }

  ngOnInit(): void {
    console.log('login OnInit called...');
    if (this.loginService.isLoggedIn()) {
      this.loading = true;
      this.getUser();
    }
  }

  onSubmit() {
    console.log('login onSubmit called...');
    this.error = false;
    for (let entry in this.model) {
      localStorage.setItem(entry, this.model[entry]);
    }
    setTimeout(()=>{}, 3000);
    console.log('onSubmit done!');
    this.initiateTokenHandshake(this.model);
  }

  initiateTokenHandshake(loginFormModel: {'telephone':string, 'password':string}) {
    let loginPhone = loginFormModel.telephone;
    let loginPass = loginFormModel.password;
    this.loginService.getTokenForUser(loginPhone, loginPass).subscribe(
      data => {
        console.log('Login credentials OK');
        this.token = JSON.parse(JSON.stringify(data))._body;
        localStorage.setItem('token', this.token);
        this.loading = true;
        this.getUser();
      },
      error => {
        console.log('Login credentials not accepted!');
        this.handleError(error);
      }
    );
  }

  getUser() {
    this.userService.getUser(parseInt(localStorage.getItem('telephone'))).subscribe(
      response => {
        console.log('Response received');
        this.userService.user = response.json();
        for (let entry in this.userService.user) {
          localStorage.setItem(entry, this.userService.user[entry]);
        }
        this.setGroups();
      },
      error => {
        console.log('Error fetching user data');
        this.handleError(error);
      }
    );
  }

  setGroups() {
    this.userService.getGroups().subscribe(
      data => {
        this.userService.user.groups = data.json();
      },
      error => {
        console.log('Error getting groups');
        this.userService.user.groups = [];
      },
      () => {
        this.navigateHome();
      });
  }

  navigateHome() {
    console.log('Navigate to Home...');
    setTimeout(
      () => { this.router.navigate(['home']); },
      500
    );
  }

  handleError(error) {
    this.loading = false;
    this.error = true;
    this.errorMsg = error.json().message;
    console.log('parsed error message: ' + this.errorMsg);
  }
}
