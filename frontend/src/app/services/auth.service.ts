import {inject, Injectable} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {catchError, map, Observable, tap} from "rxjs";
import {HttpClient, HttpErrorResponse, HttpStatusCode} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    // private oidcSecurityService: OidcSecurityService,
    private http: HttpClient
  ) {
    // Check authentication state when the service is initialized
    // this.oidcSecurityService.checkAuth().subscribe(({ isAuthenticated }) => {
    //   console.log('User is authenticated:', isAuthenticated);
    // });
  }

  /** Start the login flow */
  login(): void {
    window.location.href = `${environment.apiGatewayUrl}/auth/login`;
  }

  // /** Log out the user */
  // logout(): void {
  //   this.oidcSecurityService.logoff();
  // }
  //
  // /** Get the authentication state as an Observable */
  // isAuthenticated(): Observable<boolean> {
  //   return this.oidcSecurityService.isAuthenticated();
  // }

  /** Get the access token */
  getAccessToken(): Observable<any> {
    return this.http.get(`${environment.apiGatewayUrl}/auth/access-token`,
      {
        withCredentials: true,
      }
    );
  }

  checkLoginStatus(): Observable<any> {
    return this.http.get(`${environment.apiGatewayUrl}/auth/status`,
      {
        withCredentials: true,
        headers: {
          'Content-Type': 'application/json',
          //'Access-Control-Allow-Origin': 'http://localhost:4200',
        }
      }
    );
  }

  checkLoginStatus1(): Observable<any> {
    return this.http.get(`${environment.apiGatewayUrl}/auth/status`,
      {
        withCredentials: true
      }
    );
  }

  // /** Get the refresh token */
  // getRefreshToken(): Observable<string | null> {
  //   return this.http.get<{ access_token: string }>(`${environment.apiGatewayUrl}/auth/refresh-token`).pipe(
  //     map(response => response.access_token)
  //   );
  // }
  //
  // /** Get the user profile information */
  // getUserData(): Observable<any> {
  //   return this.oidcSecurityService.userData$;
  // }
}
