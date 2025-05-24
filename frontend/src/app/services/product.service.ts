import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {Product} from "../models/product.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Cart} from "../models/cart.model";
import {BaseService} from "./base.service";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService extends BaseService {

  constructor(
    http: HttpClient,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    super(http);
  }

  filterAndPaging(filterByName: string, page: number, size: number): Observable<any> {
    return this.doGet(environment.productServiceUrl)
      .pipe(
        tap({
          next: (response) => {
            // Handle success response
            console.log('Product list:', response);
          },
          error: (error) => {
            // Handle error response
            console.error('Error retrieving product list:', error);
            // Optionally, show an error message
            this.snackBar.open('Error retrieving product list', 'Close', { duration: 2000 });
          }
        })
      );
  }
}
