
import {AuthService} from "../services/auth.service";
import {inject} from "@angular/core";
import {CanActivateFn, Router} from "@angular/router";
import {map, of} from "rxjs";

export const AuthGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return of(true);
  // return authService.isAuthenticated().pipe(
  //   map(isAuthenticated => {
  //     if (isAuthenticated) {
  //       return true; // Allow access
  //     } else {
  //       authService.login(); // Redirect to login page
  //       return false; // Block access
  //     }
  //   })
  // );
};
