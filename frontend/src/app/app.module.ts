import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule, FormBuilder, NgControl } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing';
import { HomeModule } from './components/DNBonus/home.module';

import { Register } from './components/register.component';
import { Login } from './components/login.component';

import { RegisterService } from './services/register.service';
import { LoginService } from './services/login.service';
import { UserService } from './services/user.service';
import { BackendService } from './services/backend.service';

import { NotificationBarModule } from 'angular2-notification-bar';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HomeModule,
    AppRoutingModule,
    NotificationBarModule,
    ReactiveFormsModule
  ],
  declarations: [
    AppComponent,
    Register,
    Login
  ],
  providers: [
    RegisterService,
    LoginService,
    UserService,
    BackendService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
