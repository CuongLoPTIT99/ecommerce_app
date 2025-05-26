import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {CommonModule} from "@angular/common";
import {Router, RouterModule} from "@angular/router";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatBadgeModule} from "@angular/material/badge";
import {MatMenuModule} from "@angular/material/menu";
import {MatDividerModule} from "@angular/material/divider";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AuthService} from "../../services/auth.service";
import {NotificationService} from "../../services/notification.service";
import {RealtimeService} from "../../services/realtime.service";
import {Button, ButtonDirective} from "primeng/button";
import {FormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {Menu, MenuModule} from "primeng/menu";
import {TooltipModule} from "primeng/tooltip";
import {MenuItem} from "primeng/api";
import {CreateOrderComponent} from "../../components/order/create-order/create-order.component";
import {DialogModule} from "primeng/dialog";
import {ViewOrderComponent} from "../../components/order/view-order/view-order.component";

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
    MatDividerModule,
    ButtonDirective,
    FormsModule,
    InputTextModule,
    MenuModule,
    TooltipModule,
    Button,
    CreateOrderComponent,
    DialogModule,
    ViewOrderComponent
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  @Output() menuToggled = new EventEmitter<void>();
  @Output() emitToggleSidebar = new EventEmitter<void>();
  @ViewChild('userMenu') menu!: Menu;

  searchQuery = '';
  cartCount = 3;
  wishlistCount = 5;

  visibleMyCart = false;

  constructor(
    private router: Router,
    private httpClient: HttpClient,
    private authService: AuthService,
    private notificationService: NotificationService,
    private realtimeService: RealtimeService
  ) {
    this.realtimeService.subscribeRealtimeData('notification', this.handleRealtimeData);
  }

  userMenuItems: MenuItem[] = [
    {
      label: 'My Account',
      icon: 'pi pi-user',
      routerLink: '/account'
    },
    {
      label: 'My Orders',
      icon: 'pi pi-shopping-bag',
      routerLink: '/orders'
    },
    {
      label: 'Wishlist',
      icon: 'pi pi-heart',
      routerLink: '/wishlist'
    },
    {
      separator: true
    },
    {
      label: 'Settings',
      icon: 'pi pi-cog',
      routerLink: '/settings'
    },
    {
      label: 'Logout',
      icon: 'pi pi-sign-out',
      command: () => this.logout()
    }
  ];

  logout() {
    // Implement logout logic
    console.log('Logging out...');
  }

  toggleUserMenu(event: MouseEvent){
    this.menu.toggle(event);
    setTimeout(() => {
      const menuEl = document.querySelector('.p-menu.p-menu-overlay') as HTMLElement;
      if (menuEl) {
        menuEl.style.position = 'fixed';
        menuEl.style.top = '46px';
        menuEl.style.right = '40px';
      }
    }, 0);
  }

  handleLogin(){
    // this.menu.toggle(event);
    // setTimeout(() => {
    //   const menuEl = document.querySelector('.p-menu.p-menu-overlay') as HTMLElement;
    //   if (menuEl) {
    //     menuEl.style.position = 'fixed';
    //     menuEl.style.top = '46px';
    //     menuEl.style.right = '40px';
    //   }
    // }, 0);
  }

  handleRealtimeData = () => {

  }

  toggleSidebar(): void {
    this.emitToggleSidebar.emit();
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

  closeCreateOrderDialog() {
    this.visibleMyCart = false;
  }




  protected readonly environment = environment;
}
