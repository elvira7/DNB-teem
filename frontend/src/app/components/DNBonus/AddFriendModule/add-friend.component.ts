/**
 * Created by elvira on 21/04/2017.
 */
import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';

import {NotificationBarService, NotificationType} from 'angular2-notification-bar';

@Component({
  selector: 'add-friend',
  templateUrl: 'add-friend.component.html',
  styleUrls: ['add-friend.component.css'],
  host: {'class': 'transparent-background'}
})


export class AddFriendComponent implements OnInit {

  form: {
    firstName: string,
    lastName: string,
    email: string,
    message: string
  };

  constructor(private notificationBarService: NotificationBarService) {
  }

  ngOnInit(): void {
    console.log('AddFriendComponent onInit called...');
    this.resetAllFields();
  }

  sendMessage(): void {
    this.notificationBarService.create({
      message: 'Email successfully sent',
      type: NotificationType.Info
    });
    this.resetAllFields();
  }

  resetAllFields() {
    this.form = {firstName: '', lastName: '', email: '', message: ''};
  }
}
