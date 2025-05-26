import {Component, EventEmitter, Output} from '@angular/core';
import {Product} from "../../../models/product.model";
import {CartService} from "../../../services/cart.service";
import {FormsModule} from "@angular/forms";
import {InputNumberModule} from "primeng/inputnumber";
import {FloatLabelModule} from "primeng/floatlabel";
import {ButtonGroupModule} from "primeng/buttongroup";
import {Button} from "primeng/button";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'create-order',
  standalone: true,
  imports: [
    FormsModule,
    InputNumberModule,
    FloatLabelModule,
    ButtonGroupModule,
    Button,
    ToastModule
  ],
  templateUrl: './create-order.component.html',
  styleUrl: './create-order.component.scss'
})
export class CreateOrderComponent {
  @Output() emitCloseAction: EventEmitter<any> = new EventEmitter();

  quantity:any = 1;
  product: Product = {
    id: 1,
    name: 'Smartphone',
    description: 'iPhone 16 Pro Max 256GB',
    price: 799.99,
    imageUrl: 'https://cdn.tgdd.vn/Products/Images/42/329149/iphone-16-pro-max-sa-mac-thumb-1-600x600.jpg'
  };

  constructor(
    private cartService: CartService,
  ) {
  }

  onCancel() {
    this.emitCloseAction.emit();
  }

  onSubmit() {
    // Handle form submission logic here
    // this.dialogActions.close();
  }

  changeQuantity() {
    console.log('Quantity changed:', this.quantity);
    if (this.quantity === null || this.quantity === undefined || this.quantity === '' || this.quantity < 1) {

      this.quantity = 1;
      console.log('alo:', this.quantity);
    }
  }

  handleBuyNow() {
    this.cartService.addToCart({} as any).subscribe({
      next: (response)=> {
        this.emitCloseAction.emit();
      }
    })
  }
}
