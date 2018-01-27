import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'phase3',
  templateUrl: 'phase3.component.html',
  styleUrls:['phase3.component.css'],
  host: { 'class': 'transparent-background' }
})
export class Phase3Component implements OnInit {

  constructor() { }

  ngOnInit(): void {
    console.log('Phase3Component onInit called...');
  }
}
