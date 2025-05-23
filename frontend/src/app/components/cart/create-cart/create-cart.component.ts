import {Component} from '@angular/core';
import {MatDialog, MatDialogActions, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {Product} from "../../../models/product.model";

@Component({
  selector: 'create-cart',
  standalone: true,
  imports: [
    MatDialogContent,
    MatIcon,
    MatDialogActions,
    MatButton,
    MatIconButton,
    MatDialogTitle
  ],
  templateUrl: './create-cart.component.html',
  styleUrl: './create-cart.component.scss'
})
export class CreateCartComponent {
  quantity:any = 1;
  product: Product = {
    id: 1,
    name: 'Smartphone',
    description: 'iPhone 16 Pro Max 256GB',
    price: 799.99,
    imageUrl: 'https://cdn.tgdd.vn/Products/Images/42/329149/iphone-16-pro-max-sa-mac-thumb-1-600x600.jpg'
  };

  constructor(
    private dialog: MatDialog,
  ) {
  }

  onCancel() {
    this.dialog.closeAll();
  }

  onSubmit() {
    // Handle form submission logic here
    // this.dialogActions.close();
  }

  decrementQuantity() {
    if (this.quantity <= 1) {
      return;
    }
    this.quantity--;
  }

  incrementQuantity() {
    this.quantity++;
  }

  addToCart() {

  }
}
