import {inject, Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpStatusCode, HttpInterceptorFn
} from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import {AuthService} from "../services/auth.service";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === HttpStatusCode.Unauthorized) {
        authService.login(); // Redirect to login page
      } else if (error.status === HttpStatusCode.Forbidden) {
        console.error('Access denied');
      }
      throw error; // Rethrow the error
    })
  );
}
