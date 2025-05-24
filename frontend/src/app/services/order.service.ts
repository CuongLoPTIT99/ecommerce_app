import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {Product} from "../models/product.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Cart} from "../models/cart.model";
import {BaseService} from "./base.service";
import {Order} from "../models/order.model";

@Injectable({
  providedIn: 'root'
})
export class OrderService extends BaseService {

  constructor(
    http: HttpClient,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    super(http);
  }

  createOrder(order: Order) {
    this.doPost(`${environment.cartServiceUrl}`, order).subscribe({
      next: (response)=> {
        // Handle success response
        console.log('Order created:', response);
        // Optionally, show a success message or navigate to the cart page
        this.snackBar.open('Order created!', 'Close', {duration: 2000});
      },
      error: (error) => {
        // Handle error response
        console.error('Error creating order:', error);
        // Optionally, show an error message
        this.snackBar.open('Error creating order', 'Close', {duration: 2000});
      }
    })
  }

  cancelOrder(orderId: number, cancelReason: string) {
    this.doPost(`${environment.cartServiceUrl}/cancel`, {
      orderId: orderId,
      cancelReason: cancelReason
    }).subscribe({
      next: (response)=> {
        // Handle success response
        console.log('Order cancelled:', response);
        // Optionally, show a success message or navigate to the cart page
        this.snackBar.open('Order cancelled!', 'Close', {duration: 2000});
      },
      error: (error) => {
        // Handle error response
        console.error('Error cancelling order:', error);
        // Optionally, show an error message
        this.snackBar.open('Error cancelling order', 'Close', {duration: 2000});
      }
    })
  }
}
