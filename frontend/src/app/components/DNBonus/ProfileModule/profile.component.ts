/**
 * Created by elvira on 24/11/2016.
 */
import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css'],
  host: {'class': 'transparent-background'}
})

export class ProfileComponent implements OnInit {

  firstName;
  lastName;

  get score() {
    return Math.round(this.userService.user.score * 100) / 100;
  }

  set score(value: number) {
    this.userService.user.score = value;
  }

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.firstName = this.userService.user.firstName;
    this.lastName = this.userService.user.lastName;
  }

}
