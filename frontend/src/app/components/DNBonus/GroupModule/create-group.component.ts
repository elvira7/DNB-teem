/**
 * Created by elvira on 02/03/2017.
 */
import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {NgForm} from '@angular/forms/src/directives';

import {GroupService} from '../../../services/group.service';
import {UserService} from '../../../services/user.service';

import {User} from "../../../models/User";


@Component({
  selector: 'create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css'],
  host: {'class': 'transparent-background'}
})

export class CreateGroupComponent {
  groupName: string;
  invitedUser: string;

  constructor(private groupService: GroupService,
              private userService: UserService,
              private router: Router) {

  }

  onSubmit(form: NgForm) {
    this.groupName = form.value['groupName'];
    let creatorPhone: number = parseInt(localStorage.getItem('telephone'));
    this.groupService.createGroup(this.groupName, creatorPhone).subscribe(
      data => {
        console.log('Group "' + this.groupName + '" successfully created!');
        this.userService.updateUserGroups();
        this.router.navigate(['main-group']);
      },
      error => {
        console.log('Error: could not create group');
        console.log(error);
      }
    );
  }

}
