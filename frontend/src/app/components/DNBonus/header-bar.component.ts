import {Component} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
@Component({
  selector: 'header-bar',
  styleUrls: ['header-bar.component.css'],
  templateUrl: './header-bar.component.html'
})

export class HeaderBarComponent {
  user: User;

  get score() {
    return Math.round(this.userService.user.score * 100) / 100;
  }

  get bankAccount(){
    return Math.round(this.userService.user.money * 100) / 100;
  }

  constructor(private loginService: LoginService,
              private userService: UserService) {
  }

  logout() {
    this.loginService.token = null;
    localStorage.clear();
  }

  ngOnInit(): void {
    console.log('HeaderBar onInit...');
    console.log(this.userService.user);
    this.user = this.userService.user;
    console.log(this.user);

  }


}
