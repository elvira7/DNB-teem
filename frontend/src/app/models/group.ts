
import {User} from "./user";
export class Group{
  public users: User[];
  public loans: [{}];
  public potId: number;
  public name: string;
  public availableMoney: number;
  public totalMoney: number;
  public created: Date;


  constructor(name: string) {
    this.name = name;
  }

}
