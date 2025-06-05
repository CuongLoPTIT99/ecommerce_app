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
import {MenuItem, MessageService} from "primeng/api";
import {CreateOrderComponent} from "../../components/order/create-order/create-order.component";
import {DialogModule} from "primeng/dialog";
import {ViewOrderComponent} from "../../components/order/view-order/view-order.component";
import {ViewCartComponent} from "../../components/cart/view-cart/view-cart.component";
import {ToastModule} from "primeng/toast";
import {Cart} from "../../models/cart.model";
import {Order} from "../../models/order.model";
import {BadgeModule} from "primeng/badge";
import {OverlayPanel, OverlayPanelModule} from "primeng/overlaypanel";
import {DataViewModule} from "primeng/dataview";
import {InputNumberModule} from "primeng/inputnumber";
import {TagModule} from "primeng/tag";
import {Product} from "../../models/product.model";

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
    ViewOrderComponent,
    ViewCartComponent,
    ToastModule,
    BadgeModule,
    OverlayPanelModule,
    DataViewModule,
    InputNumberModule,
    TagModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  @Output() menuToggled = new EventEmitter<void>();
  @Output() emitToggleSidebar = new EventEmitter<void>();
  @ViewChild('userMenu') menu!: Menu;
  @ViewChild('notificationPanel') notificationPanel!: OverlayPanel;

  searchQuery = '';
  isLoggedIn = true;

  visibleMyCart = false;
  visibleCreateOrder = false;
  visibleViewOrder = false;
  visibleMyOrder = false;

  order2Buy: Order = {};

  products: Product[] = [
    {
      "id": 1,
      "name": "iPhone 16 Pro Max 256GB",
      "brand": "iPhone",
      "description": "iPhone 16 Pro Max 256GB",
      "price": 799.99,
      "imageUrl": "https://cdn.tgdd.vn/Products/Images/42/329149/iphone-16-pro-max-sa-mac-thumb-1-600x600.jpg",

    },
    {
      "id": 2,
      "name": "Samsung Galaxy S25 Edge 5G 12GB/512GB",
      "brand": "Samsung",
      "description": "Samsung Galaxy S25 Edge 5G 12GB/512GB",
      "price": 1299.99,
      "imageUrl": "https://cdn.tgdd.vn/Products/Images/42/335955/samsung-galaxy-s25-edge-blue-thumb-600x600.jpg",

    },
    {
      "id": 3,
      "name": "Samsung Galaxy A06 5G 6GB/128GB",
      "brand": "Samsung",
      "description": "Samsung Galaxy A06 5G 6GB/128GB",
      "price": 199.99,
      "imageUrl": "https://cdn.tgdd.vn/Products/Images/42/335234/samsung-galaxy-a06-5g-black-thumbn-600x600.jpg",

    },
    {
      "id": 4,
      "name": "Samsung Galaxy A36 5G 12GB/256GB",
      "brand": "Samsung",
      "description": "Samsung Galaxy A36 5G 12GB/256GB",
      "price": 249.99,
      "imageUrl": "https://cdn.tgdd.vn/Products/Images/42/334930/samsung-galaxy-a36-5g-green-thumb-600x600.jpg",

    },
    {
      "id": 34,
      "name": "HONOR X8c 8GB/256GB",
      "brand": "HONOR",
      "description": "HONOR X8c 8GB/256GB",
      "price": 499.99,
      "imageUrl": "https://cdnv2.tgdd.vn/mwg-static/tgdd/Products/Images/42/335792/honor-x8c-green-thumb-638778940941716593-600x600.jpg",

    },
    {
      "id": 35,
      "name": "realme 14T 5G 8GB/256GB",
      "brand": "realme",
      "description": "realme 14T 5G 8GB/256GB",
      "price": 899.99,
      "imageUrl": "https://cdn.tgdd.vn/Products/Images/42/336619/realme-14t-5g-black-thumb-600x600.jpg",

    }
  ];

  constructor(
    private authService: AuthService,
    private realtimeService: RealtimeService
  ) {
    // this.authService.isLoggedIn().subscribe({
    //   next: (response) => {
    //     this.isLoggedIn = response?.isLoggedIn ?? false;
    //   },
    //   error: (error) => {
    //     console.error('Error checking login status', error);
    //     this.isLoggedIn = false;
    //   }
    // });
    this.realtimeService.subscribeRealtimeData('notification', this.handleRealtimeData);
  }

  userMenuItems: MenuItem[] = [
    {
      label: 'My Account',
      icon: 'pi pi-user',
      routerLink: '/account'
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
      command: () => this.authService.logout()
    }
  ];

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

  login(){
    this.authService.login();
  }

  handleRealtimeData = () => {

  }

  toggleSidebar(): void {
    this.emitToggleSidebar.emit();
  }

  openCreateOrder(cart: Cart) {
    this.order2Buy = {
      product: cart.product,
      quantity: cart.quantity,
      totalPrice: (cart?.product?.price ?? 0) * (cart.quantity ?? 0)
    }
    this.visibleCreateOrder= true;
  }

  openViewOrder(order: Order) {
    this.order2Buy = order;
    this.visibleViewOrder= true;
  }

  viewNotification(event: Event) {
    this.notificationPanel.toggle(event);
    setTimeout(() => {
      const notificationEl = document.querySelector('.p-overlaypanel') as HTMLElement;
      if (notificationEl) {
        notificationEl.style.position = 'fixed';
      }
    }, 0);
  }

  closeViewMyCartDialog() {
    this.visibleMyCart = false;
  }

  closeCreateOrderDialog() {
    this.visibleCreateOrder = false;
  }

  closeViewOrderDialog() {
    this.visibleViewOrder = false;
  }

  closeViewMyOrderDialog() {
    this.visibleMyOrder = false;
  }
}
