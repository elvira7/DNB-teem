import { Injectable } from "@angular/core";
import { User } from '../models/User';
import {BackendService} from "./backend.service";


@Injectable()
export class RegisterService {
  constructor(
    private backendService: BackendService
  ) { }

  registerUser(user: User) {
    return this.backendService.registerUSer(user);
  }
}
