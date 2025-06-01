// user.model.ts
import {Shipment} from "./shipment.model";
import {Product} from "./product.model";

export interface Order {
  id?: number;
  customerId?: number;
  productId?: number;
  quantity?: number;
  totalPrice?: number;
  status?: string;
  cancelReason?: string;
  product?: Product;
  shipment?: Shipment;
}
