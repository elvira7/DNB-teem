import { NgForm } from '@angular/forms/src/directives';
import { Component } from '@angular/core';
import { User } from '../models/User';

import { RegisterService } from '../services/register.service';
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css']
})

export class Register {
  newUser = new User();
  registered: boolean = false;
  passwordConfirmed: boolean = true;
  phoneValid: boolean = true;
  backendError: string;

  constructor(
    private registerService: RegisterService,
    private userService: UserService,
    private router: Router
  ) {
  }

  onSubmit(formModel: NgForm) {
    if (this.formValid()) {
      this.registerService.registerUser(this.newUser).subscribe(
        registered => {
          this.registered = true;
          this.userService.getUser(this.newUser.telephone).subscribe(
            (user)=>{
              this.userService.user = user.json();
              for (let entry in this.userService.user) {
                localStorage.setItem(entry, this.userService.user[entry]);
              }
              this.userService.getGroups().subscribe(
                data => {
                  this.userService.user.groups = data.json();
                  },
                error => {
                  console.log('Error fetching groups');
                  this.userService.user.groups = [];
                  },
                () => {
                  setTimeout(
                    () => { this.router.navigate(['home']); },
                    1500
                  );
                });
            },
            error => {
              console.log('Error fetching user data');
            }
          );

        },
        error => {
          console.log('Error response from backend');
          this.backendError =  error.json().message;
        });
    }
  }

  formValid(): boolean {
    this.passwordConfirmed = this.isPasswordConfirmed();
    this.phoneValid = !isNaN(this.newUser.telephone);

    return this.passwordConfirmed && this.phoneValid;
  }

  isPasswordConfirmed(): boolean {
    return this.newUser.password == this.newUser.confirmPassword;
  }
}


