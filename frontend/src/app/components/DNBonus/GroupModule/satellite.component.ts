import {Component, Input, OnInit} from '@angular/core';
import any = jasmine.any;

@Component({
  selector: '[app-satellite]',
  templateUrl: 'satellite.component.html'
})

export class SatelliteComponent implements OnInit {
  private __text: string;
  private __button_x: number;
  private __button_y: number;
  private _clicked: (...any) => {};

  @Input()
  get text(): string {
    return this.__text;
  }

  set text(text) {
    this.__text = text ? '' + text : 'no-text-set';
  }

  @Input('x')
  get button_x(): number {
    return this.__button_x;
  }

  set button_x(value: number) {
    this.__button_x = Math.floor(value);
  }

  @Input('y')
  get button_y(): number {
    return this.__button_y;
  }

  set button_y(value: number) {
    this.__button_y = Math.floor(value);
  }

  @Input('radius') button_radius: number;
  @Input() font_size: number;

  @Input()
  get clicked(): (...any) => {} {
    return this._clicked;
  }

  set clicked(value: (...any) => {}) {
    if (value) {
      this._clicked = value;
    } else {
      this._clicked = () => {
        return () => console.log('no function attached.');
      };
    }
  }

  constructor() {
  }

  ngOnInit() {
  }

}
