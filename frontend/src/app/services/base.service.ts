import {inject, Injectable} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {catchError, map, Observable, tap} from "rxjs";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse, HttpStatusCode} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Client} from "@stomp/stompjs";
import SockJS from "sockjs-client";

@Injectable({
  providedIn: 'root'
})
export class BaseService {
  protected headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  });

  constructor(
    private http: HttpClient
  ) {
    // Check authentication state when the service is initialized
    // this.oidcSecurityService.checkAuth().subscribe(({ isAuthenticated }) => {
    //   console.log('User is authenticated:', isAuthenticated);
    // });

  }

  protected doPost(url: string, data: any): Observable<any> {
    return this.http.post<any>(url, data, { headers: this.headers });
  }

  protected doGet(url: string): Observable<any> {
    return this.http.post<any>(url, { headers: this.headers });
  }

  protected doPut(url: string): Observable<any> {
    return this.http.put<any>(url, { headers: this.headers });
  }

  protected doDelete(url: string): Observable<any> {
    return this.http.delete<any>(url, { headers: this.headers });
  }
}
