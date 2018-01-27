import { UserService } from '../../services/user.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { HomeRoutingModule} from './home.routing';
import { GroupService } from '../../services/group.service';

import { HomeComponent } from './home.component';
import { HeaderBarComponent } from './header-bar.component';
import { SideBarComponent } from './side-bar.component';

import { ProfileComponent } from './ProfileModule/profile.component';
import { MarketComponent } from './MarketModule/market.component';
import { MainGroupComponent } from './GroupModule/main-group.component';
import { StatsComponent} from './StatsModule/stats.component';
import { AddFriendComponent } from './AddFriendModule/add-friend.component';

import { GroupDetailsComponent } from './GroupModule/group-details.component';
import { CreateGroupComponent } from './GroupModule/create-group.component';
import { SatelliteComponent } from './GroupModule/satellite.component';
import {Phase3Component} from "./Phase3Module/phase3.component";


@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        HomeRoutingModule
    ],
    declarations: [
        HomeComponent,
        HeaderBarComponent,
        SideBarComponent,
        ProfileComponent,
        MarketComponent,
        MainGroupComponent,
        GroupDetailsComponent,
        CreateGroupComponent,
        StatsComponent,
        SatelliteComponent,
        AddFriendComponent,
      Phase3Component
        ],
        providers: [
            GroupService,
            UserService
        ]
})
export class HomeModule {}
