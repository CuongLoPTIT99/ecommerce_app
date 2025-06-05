import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Product} from "../../../models/product.model";
import {CartService} from "../../../services/cart.service";
import {FormsModule} from "@angular/forms";
import {InputNumberModule} from "primeng/inputnumber";
import {FloatLabelModule} from "primeng/floatlabel";
import {ButtonGroupModule} from "primeng/buttongroup";
import {Button} from "primeng/button";
import {ToastModule} from "primeng/toast";
import {AccordionModule} from "primeng/accordion";
import {PanelModule} from "primeng/panel";
import {ShipmentComponent} from "../../shipment/shipment.component";
import {Order} from "../../../models/order.model";
import {TagModule} from "primeng/tag";
import {OrderService} from "../../../services/order.service";
import {MessageService} from "primeng/api";
import {Shipment} from "../../../models/shipment.model";

@Component({
  selector: 'create-order',
  standalone: true,
  imports: [
    FormsModule,
    InputNumberModule,
    FloatLabelModule,
    ButtonGroupModule,
    Button,
    ToastModule,
    AccordionModule,
    PanelModule,
    ShipmentComponent,
    TagModule
  ],
  templateUrl: './create-order.component.html',
  styleUrl: './create-order.component.scss'
})
export class CreateOrderComponent {
  @Output() emitCloseAction: EventEmitter<any> = new EventEmitter();
  @Input() order: Order = {
    quantity: 1,
    shipment: {} as Shipment,
  };
  @Input() type: 'create' | 'view' = 'create';

  constructor(
    private orderService: OrderService,
    private messageService: MessageService,
  ) {
  }

  changeQuantity() {
    this.order.totalPrice = (this.order?.product?.price ?? 0) * (this.order?.quantity ?? 0);
  }

  buyNow() {
    this.orderService.createOrder({
      productId: this.order.product?.id,
      customerId: 1,
      ...this.order
    }).subscribe({
      next: (response)=> {
        if (response?.isSuccess) {
          this.emitCloseAction.emit();
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Order created successfully!'});
        } else {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: response?.message});
        }
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error creating order'});
      }
  });
  }

  saveOrder() {
    this.orderService.updateOrder(this.order).subscribe({
      next: (response)=> {
        if (response?.isSuccess) {
          this.emitCloseAction.emit();
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Order updated successfully!'});
        } else {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: response?.message});
        }
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error updating order'});
      }
    });
  }

  onCancel() {
    if (this.order.id) {
      this.orderService.cancelOrder(this.order.id, '').subscribe({
        next: (response)=> {
          if (response?.isSuccess) {
            this.emitCloseAction.emit();
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Order cancelled successfully!'});
          } else {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: response?.message});
          }
        },
        error: (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error cancelling order'});
        }
      });
    } else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Order ID is not found!'});
    }
  }
}
