import {Product} from "./product.model";

export interface Cart {
  id?: number;
  customerId?: number;
  productId?: number;
  quantity?: number;
  product?: Product;
}
