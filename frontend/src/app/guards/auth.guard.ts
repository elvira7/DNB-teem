// Taken and adapted from:
// http://jasonwatmore.com/post/2016/08/16/angular-2-jwt-authentication-example-tutorial#home-component-ts 
import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { LoginService } from '../services/login.service';
 
@Injectable()
export class AuthGuard implements CanActivate {
 
    constructor(
        private router: Router,
        private loginService : LoginService) { }
 
    canActivate() {
        console.log('AuthGuard.canActivate');
        return this.loginService.isLoggedIn();        
    }
}