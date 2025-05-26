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
    NgForOf
  ],
  templateUrl: './view-order.component.html',
  styleUrl: './view-order.component.scss'
})
export class ViewOrderComponent {
  @Output() emitCloseAction: EventEmitter<any> = new EventEmitter();
  products: Product[] = [];

  constructor(
    private productService: ProductService,
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

  closeDialog() {
    this.emitCloseAction.emit();
  }
}
