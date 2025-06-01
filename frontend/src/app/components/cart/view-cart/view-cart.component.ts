import {Component, EventEmitter, Input, OnChanges, OnDestroy, Output, SimpleChanges} from '@angular/core';
import {Button} from "primeng/button";
import {DataViewModule, DataViewPageEvent} from "primeng/dataview";
import {NgClass, NgForOf} from "@angular/common";
import {MessageService, PrimeTemplate} from "primeng/api";
import {TagModule} from "primeng/tag";
import {Product} from "../../../models/product.model";
import {ProductService} from "../../../services/product.service";
import {FloatLabelModule} from "primeng/floatlabel";
import {InputNumberModule} from "primeng/inputnumber";
import {StyleClassModule} from "primeng/styleclass";
import {CartService} from "../../../services/cart.service";
import {PaginatorModule, PaginatorState} from "primeng/paginator";
import {ToastModule} from "primeng/toast";
import {Cart} from "../../../models/cart.model";

@Component({
  selector: 'view-cart',
  standalone: true,
  imports: [
    Button,
    DataViewModule,
    NgForOf,
    PrimeTemplate,
    TagModule,
    NgClass,
    FloatLabelModule,
    InputNumberModule,
    StyleClassModule,
    PaginatorModule,
    ToastModule
  ],
  templateUrl: './view-cart.component.html',
  styleUrl: './view-cart.component.scss'
})
export class ViewCartComponent {
  @Output() emitCloseAction: EventEmitter<any> = new EventEmitter();
  @Output() emitCreateOrder: EventEmitter<any> = new EventEmitter();
  products: Product[] = [];

  pageSize = 5;
  pageIndex = 0;
  totalRecord = 0;

  changeQuantityQueue: number[] = [];

  constructor(
    private messageService: MessageService,
    private productService: ProductService,
    private cartService: CartService,
  ) {
    this.viewMyCart();
  }

  viewMyCart() {
    this.cartService.viewMyCart(1, this.pageIndex, this.pageSize).subscribe({
      next: (response) => {
        this.totalRecord = response?.totalElements;
        this.products = response.content;
      }
    });
  }

  removeCart(cartId: number) {
    this.cartService.removeFromCart(cartId).subscribe({
      next: (response) => {
        this.viewMyCart()
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Product removed from cart!'});
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error removing product from cart'});
      }
    });
  }

  buyNow(cart: Cart) {
    this.emitCloseAction.emit();

    this.emitCreateOrder.emit(cart);
  }

  onPageChange(event: PaginatorState) {
    this.pageIndex = event.page ?? 0;
    this.viewMyCart();
  }

  onChangeQuantity(itemId: number, quantity: number) {
    // Debounce the quantity change to avoid too many requests
    if (this.changeQuantityQueue.length > 0) {
      this.changeQuantityQueue.forEach(timeoutId => clearTimeout(timeoutId));
    }
    this.changeQuantityQueue.push(
      setTimeout(() => {
        this.cartService.updateCart({
          id: itemId,
          quantity: quantity
        }).subscribe();
      }, 1000)
    );
  }
}
