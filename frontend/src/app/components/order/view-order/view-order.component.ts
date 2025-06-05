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
import {ConfirmationService, MessageService} from "primeng/api";
import {OrderService} from "../../../services/order.service";
import {InputTextModule} from "primeng/inputtext";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {Order} from "../../../models/order.model";
import {PaginatorModule, PaginatorState} from "primeng/paginator";
import {Observable} from "rxjs";

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
    InputTextModule,
    ConfirmDialogModule,
    PaginatorModule
  ],
  templateUrl: './view-order.component.html',
  styleUrl: './view-order.component.scss',
  providers: [ConfirmationService]
})
export class ViewOrderComponent {
  @Output() emitCloseAction: EventEmitter<any> = new EventEmitter();
  @Output() emitViewOrder: EventEmitter<any> = new EventEmitter();

  pageSize = 5;
  pageIndex = 0;
  totalRecord = 0;

  orders: Order[] = [];

  cancelReason: string = '';

  constructor(
    private orderService: OrderService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    this.viewMyOrder();
  }

  viewMyOrder() {
    this.orderService.viewMyOrder(1, this.pageIndex, this.pageSize).subscribe({
      next: (response) => {
        this.totalRecord = response?.totalElements;
        this.orders = response.content;
      }
    });
  }

  confirmCancelOrder(event: Event, orderId: number) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Are you sure that you want to cancel order?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon:"none",
      rejectIcon:"none",
      rejectButtonStyleClass:"p-button-text",
      accept: () => {
        this.orderService.cancelOrder(orderId, this.cancelReason).subscribe({
          next: (response) => {
            if (response?.isSuccess) {
              this.viewMyOrder();
              this.messageService.add({ severity: 'success', summary: 'Success', detail: 'The order removed from your order list!'});
            } else {
              this.messageService.add({ severity: 'error', summary: 'Error', detail: response?.message});
            }
          },
          error: (error) => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error removing order from your order list'});
          }
        });
      }
    });
  }

  viewOrder(order: Order) {
    this.emitCloseAction.emit();
    this.emitViewOrder.emit(order);
  }

  onPageChange(event: PaginatorState) {
    this.pageIndex = event.page ?? 0;
    this.viewMyOrder();
  }
}
