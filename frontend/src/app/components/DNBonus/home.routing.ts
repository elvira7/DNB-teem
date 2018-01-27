import { StatsComponent } from './StatsModule/stats.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { ProfileComponent } from './ProfileModule/profile.component';
import { MarketComponent } from './MarketModule/market.component';
import { MainGroupComponent } from './GroupModule/main-group.component';
import { GroupDetailsComponent } from './GroupModule/group-details.component';
import { CreateGroupComponent } from './GroupModule/create-group.component';
import {AddFriendComponent} from "./AddFriendModule/add-friend.component";
import {Phase3Component} from "./Phase3Module/phase3.component";


const paneRoutes: Routes = [
    { path: 'home', redirectTo: 'main-group' },
    {
        path: '', component: HomeComponent,
        children: [
            { path: 'main-group', component: MainGroupComponent },
            { path: 'create-group', component: CreateGroupComponent },
            { path: 'group-details/:id', component: GroupDetailsComponent },
            { path: 'profile', component: ProfileComponent },
            { path: 'market', component: MarketComponent },
            { path: 'stats', component: StatsComponent },
            { path: 'add-friend', component: AddFriendComponent},
            { path: 'phase3', component: Phase3Component}
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(paneRoutes)],
    exports: [RouterModule]
})

export class HomeRoutingModule { }
