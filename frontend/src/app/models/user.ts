import { Group } from './group';

export class User {
  public created: Date;
  public dnbCustomer;
  public firstName: string;
  public investments: any[];
  public invites: any[];
  public lastName: string;
  public loans: any[];
  public money: number;
  public password: string;
  public score: number;
  public telephone: number;

  public referenceNumber: number;
  public confirmPassword: string;

  public groups: Group[];

  constructor(firstName?: string) {
    this.firstName = firstName;
    this.created = new Date();
  }
}
