import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {Product} from "../models/product.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Cart} from "../models/cart.model";
import {BaseService} from "./base.service";
import {Order} from "../models/order.model";
import {MessageService} from "primeng/api";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OrderService extends BaseService {

  constructor(
    http: HttpClient,
    private messageService: MessageService
  ) {
    super(http);
  }

  createOrder(order: Order): Observable<any> {
    return this.doPost(`${environment.cartServiceUrl}`, order).pipe(
      tap({
        next: (response)=> {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Order created successfully!'});
        },
        error: (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error creating order'});
        }
      })
    );
  }

  cancelOrder(orderId: number, cancelReason: string): Observable<any> {
    return this.doPost(`${environment.cartServiceUrl}/cancel`, {
      orderId: orderId,
      cancelReason: cancelReason
    }).pipe(
      tap({
        next: (response)=> {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Order cancelled successfully!'});
        },
        error: (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error cancelling order'});
        }
      })
    );
  }
}
