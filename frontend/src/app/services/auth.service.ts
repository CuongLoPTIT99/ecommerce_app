import {inject, Injectable} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {catchError, map, Observable, tap} from "rxjs";
import {HttpClient, HttpErrorResponse, HttpResponse, HttpStatusCode} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Client} from "@stomp/stompjs";
import SockJS from "sockjs-client";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) {
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

  isLoggedIn(): Observable<any> {
    return this.http.post(`${environment.apiGatewayUrl}/api/v1/customer/token`, {},
      {
        withCredentials: true
      }
    );
  }

  logout(): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${environment.apiGatewayUrl}/auth/logout`, {},
      {
        observe: 'response',
        withCredentials: true,
      }
    ).pipe(
      tap(response => {
        if (response.status === HttpStatusCode.Ok) {
          // Redirect to the login page
          this.login();
        }
      })
    );
  }
}
