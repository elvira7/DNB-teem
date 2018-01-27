import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'stats',
  templateUrl: 'stats.component.html',
  styleUrls:['stats.component.css'],
  host: { 'class': 'transparent-background' }

})


export class StatsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    console.log('StatsComponent onInit called...');
  }
}
