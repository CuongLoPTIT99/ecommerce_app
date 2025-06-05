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
  baseUrl = environment.orderServiceUrl;

  constructor(
    http: HttpClient,
    private messageService: MessageService
  ) {
    super(http);
  }

  viewMyOrder(customerId: number, page?: number, size?: number): Observable<any> {
    return this.doGet(`${this.baseUrl}/my-order?customerId=${customerId}&page=${page}&size=${size}`);
  }

  createOrder(order: Order): Observable<any> {
    return this.doPost(`${this.baseUrl}`, order);
  }

  updateOrder(order: Order): Observable<any> {
    return this.doPut(`${this.baseUrl}`, order);
  }

  cancelOrder(orderId: number, cancelReason: string): Observable<any> {
    return this.doPost(`${this.baseUrl}/cancel`, {
      orderId: orderId,
      cancelReason: cancelReason
    });
  }
}
