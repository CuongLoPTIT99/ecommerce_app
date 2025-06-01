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
    ShipmentComponent
  ],
  templateUrl: './create-order.component.html',
  styleUrl: './create-order.component.scss'
})
export class CreateOrderComponent implements OnInit {
  @Output() emitCloseAction: EventEmitter<any> = new EventEmitter();
  @Input() order: Order | null = null;
  quantity = 0;

  constructor(
    private cartService: CartService,
  ) {
  }

  ngOnInit() {
    this.quantity = this.order?.quantity ?? 0;
  }

  onCancel() {
    this.emitCloseAction.emit();
  }

  onSubmit() {
    // Handle form submission logic here
    // this.dialogActions.close();
  }

  handleBuyNow() {
    this.cartService.addToCart({} as any).subscribe({
      next: (response)=> {
        this.emitCloseAction.emit();
      }
    })
  }
}
