import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Client} from "@stomp/stompjs";

@Injectable({
  providedIn: 'root'
})
export class RealtimeService {
  protected client: Client;

  constructor(
  ) {
    this.client = new Client({
      brokerURL: `ws://localhost:8080/ws`,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.client.activate();
  }

  subscribeRealtimeData(topic: string, handler: Function) {
    this.client.onConnect = () => {
      console.log('Connected');
      this.client.subscribe(`/realtime/${topic}`, (message) => {
        handler(JSON.parse(message.body)?.content);
      });
    };
  }

  unsubscribeRealtimeData(topic: string) {
    this.client.onConnect = () => {
      console.log('unConnected');
      this.client.unsubscribe(`/realtime/${topic}`);
    };
  }
}
