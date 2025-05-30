import {Component, EventEmitter, Output} from '@angular/core';
import {Button} from "primeng/button";
import {FloatLabelModule} from "primeng/floatlabel";
import {InputNumberModule} from "primeng/inputnumber";
import {PanelModule} from "primeng/panel";
import {ShipmentComponent} from "../../shipment/shipment.component";
import {TagModule} from "primeng/tag";
import {DataViewModule} from "primeng/dataview";
import {NgClass, NgForOf} from "@angular/common";
import {Product} from "../../../models/product.model";
import {ProductService} from "../../../services/product.service";
import {MessageService} from "primeng/api";
import {OrderService} from "../../../services/order.service";
import {InputTextModule} from "primeng/inputtext";

@Component({
  selector: 'view-order',
  standalone: true,
  imports: [
    Button,
    FloatLabelModule,
    InputNumberModule,
    PanelModule,
    ShipmentComponent,
    TagModule,
    DataViewModule,
    NgClass,
    NgForOf,
    InputTextModule
  ],
  templateUrl: './view-order.component.html',
  styleUrl: './view-order.component.scss'
})
export class ViewOrderComponent {
  @Output() emitCloseAction: EventEmitter<any> = new EventEmitter();
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

  cancelReason: string = '';

  constructor(
    private productService: ProductService,
    private orderService: OrderService,
    private messageService: MessageService
  ) {
    this.productService.filterAndPaging('', 1, 10).subscribe({
      next: (response) => {
        // this.totalRecord = response?.totalElements;
        this.products = response?.content;
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: `Error retrieving product list: ${error}`});
      }
    });
  }

  cancelOrder(orderId: number) {
    this.orderService.cancelOrder(orderId, this.cancelReason).subscribe();
  }

  closeDialog() {
    this.emitCloseAction.emit();
  }
}
