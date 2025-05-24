// user.model.ts
import {Shipment} from "./shipment.model";

export interface Order {
  id?: number;
  customerId?: number;
  productId?: number;
  quantity: number;
  totalPrice?: number;
  status?: string;
  cancelReason?: string;
  shipment?: Shipment;
}
