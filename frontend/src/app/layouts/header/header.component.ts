import {Component, EventEmitter, Output} from '@angular/core';
import {CommonModule} from "@angular/common";
import {Router, RouterModule} from "@angular/router";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatBadgeModule} from "@angular/material/badge";
import {MatMenuModule} from "@angular/material/menu";
import {MatDividerModule} from "@angular/material/divider";
import {HttpClient, HttpClientModule, HttpHandler, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AuthService} from "../../services/auth.service";
import {Client} from "@stomp/stompjs";
import {NotificationService} from "../../services/notification.service";
import {RealtimeService} from "../../services/realtime.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatBadgeModule,
    MatMenuModule,
    MatDividerModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  @Output() menuToggled = new EventEmitter<void>();

  constructor(
    private router: Router,
    private httpClient: HttpClient,
    private authService: AuthService,
    private notificationService: NotificationService,
    private realtimeService: RealtimeService
  ) {
    this.realtimeService.subscribeRealtimeData('notification', this.handleRealtimeData);
  }

  handleRealtimeData = () => {

  }

  toggleMenu(): void {
    this.menuToggled.emit();
  }

  redirect2Login(): void {
    this.authService.logout().subscribe();
    // if (ACCESS_)
    // this.httpClient.get(
    //   environment.apiGatewayUrl + '/api/v1/customer/getById/1',
    //   {
    //     headers: {
    //       'Content-Type': 'application/json',
    //       'Access-Control-Allow-Origin': '*',
    //       'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    //       'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept, Authorization, Access-Control-Allow-Origin, Access-Control-Allow-Methods'
    //     }
    //   }
    // ).subscribe((res) => {
    //   console.log(res);
    // });
    //console.log('redirect2Login', this.authService.getAccessToken());
  }

  redirect2Login1(): void {
    this.authService.checkLoginStatus().subscribe((res) => {
      console.log('redirect2Login',res);
    })
  }

  redirect2Login2(): void {
    this.authService.getUserInfo();
  }

  redirect2Login3(): void {
    this.authService.getUserInfo1();
  }



  protected readonly environment = environment;
}
