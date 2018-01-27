import {User} from './user';
import {Group} from './group';
export class Loan {

  loanId: number;
  user: User;
  pot: Group;
  createdDate: number;
  money: number;
  dueDate: number;

  constructor() {
  }

}
