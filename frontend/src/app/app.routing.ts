import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './guards/auth.guard';

import { HomeComponent } from './components/DNBonus/home.component';
import { Register } from './components/register.component';
import { Login } from './components/login.component';

const appRoutes: Routes = [
  {    path: 'register',  component: Register },
  {    path: 'login',     component: Login  },
  {    path: 'home',      component: HomeComponent, canActivate: [AuthGuard]  },
  {    path: '',    redirectTo: 'login',  pathMatch: 'full' },
  {    path: '**',  redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule],
  providers: [AuthGuard]
})

export class AppRoutingModule {}
