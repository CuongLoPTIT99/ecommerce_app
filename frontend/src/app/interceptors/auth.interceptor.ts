import {inject, Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpStatusCode, HttpInterceptorFn, HttpResponse
} from '@angular/common/http';
import {catchError, mergeMap} from 'rxjs/operators';
import {AuthService} from "../services/auth.service";
import {of, tap} from "rxjs";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  return next(req).pipe(
    mergeMap((event) => {
      if (event instanceof HttpResponse && event.status === HttpStatusCode.ResetContent) {
        console.log('Reset token successfully');
        return next(req); // Retry the request
      }
      return of(event);
    }),
    catchError((error: HttpErrorResponse) => {
      if (error.status === HttpStatusCode.Unauthorized) {
        console.error('401');
        authService.login(); // Redirect to login page
      } else if (error.status === HttpStatusCode.Forbidden) {
        console.error('Access denied');
      }
      throw error; // Rethrow the error
    })
  );
}
