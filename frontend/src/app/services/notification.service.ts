import { Injectable } from '@angular/core';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackBar: MatSnackBar) {}

  showNotification(message: string, action: string = 'Close', duration: number = 3000): void {
    const config: MatSnackBarConfig = {
      duration: duration,
      direction: 'rtl',
      horizontalPosition: 'center', // center horizontally
      verticalPosition: 'top'       // top of the screen
    };

    this.snackBar.open(message, action, config);
  }
}
