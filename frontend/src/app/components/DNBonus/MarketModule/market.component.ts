import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'market',
  templateUrl: 'market.component.html',
  styleUrls:['market.component.css'],
  host: { 'class': 'transparent-background' }
})
export class MarketComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    console.log('MarketComponent onInit called...');
  }
}
