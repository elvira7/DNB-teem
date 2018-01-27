import { Component, OnInit } from '@angular/core';

@Component({
  templateUrl: './home.component.html'
})

export class HomeComponent implements OnInit {

  constructor() {
    console.log('home constructor called...');
  }

  ngOnInit(): void {
    console.log('home OnInit called...');
  }
}
