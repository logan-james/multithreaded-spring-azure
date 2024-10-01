import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpClient, HttpResponse, HttpHeaders } from "@angular/common/http";
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private httpClient: HttpClient) { }

  private baseURL: string = 'http://localhost:8080';

  private getUrl: string = this.baseURL + '/room/reservation/v1/';
  private postUrl: string = this.baseURL + '/room/reservation/v1';
  public submitted!: boolean;
  roomsearch!: FormGroup;
  rooms!: Room[];
  request!: ReserveRoomRequest;
  currentCheckInVal!: string;
  currentCheckOutVal!: string;

  // New properties for Part B -----------------
  welcomeMessages: { [key: string]: string } = {};
  prices: { [key: string]: string } = {};
  presentationTime: string = '';
  // -------------------------------------------

  ngOnInit() {
    this.roomsearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl('')
    });

    const roomsearchValueChanges$ = this.roomsearch.valueChanges;

    // subscribe to the stream
    roomsearchValueChanges$.subscribe(x => {
      this.currentCheckInVal = x.checkin;
      this.currentCheckOutVal = x.checkout;
    });

    // Fetch data for Part B -----
    this.getWelcomeMessages();
    this.getPrices();
    this.getPresentationTime();
    // ---------------------------
  }

  onSubmit({ value, valid }: { value: Roomsearch, valid: boolean }) {
    this.getAll().subscribe(
      rooms => {
        console.log(Object.values(rooms)[0]);
        this.rooms = <Room[]>Object.values(rooms)[0];

        // START OF CHANGES FOR PROBLEM 2
        // Process the rooms to include prices in multiple currencies
        const exchangeRates = {
          USD: 1,
          CAD: 1.3, // Example exchange rate
          EUR: 0.85 // Example exchange rate
        };

        this.rooms.forEach((room) => {
          const priceUSD = parseFloat(room.price);
          room.priceUSD = priceUSD.toFixed(2);
          room.priceCAD = (priceUSD * exchangeRates.CAD).toFixed(2);
          room.priceEUR = (priceUSD * exchangeRates.EUR).toFixed(2);
        });
        // END OF CHANGES FOR PROBLEM 2
      }
    );
  }

  reserveRoom(value: string) {
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);

    this.createReservation(this.request);
  }

  createReservation(body: ReserveRoomRequest) {
    let bodyString = JSON.stringify(body); // Stringify payload
    const options = {
      headers: new HttpHeaders().append('Content-Type', 'application/json'),
    }

    this.httpClient.post(this.postUrl, body, options)
      .subscribe(res => console.log(res));
  }

  // New methods for Part B ----------------------------------------------------
  getWelcomeMessages() {
    this.httpClient.get<{ [key: string]: string }>(`${this.baseURL}/api/welcome`).subscribe(
      (data) => {
        this.welcomeMessages = data;
      },
      (error) => {
        console.error('Error fetching welcome messages:', error);
      }
    );
  }

  getPrices() {
    this.httpClient.get<{ [key: string]: string }>(`${this.baseURL}/api/prices`).subscribe(
      (data) => {
        this.prices = data;
      },
      (error) => {
        console.error('Error fetching prices:', error);
      }
    );
  }

  getPresentationTime() {
    this.httpClient.get(`${this.baseURL}/api/presentation-time`, { responseType: 'text' }).subscribe(
      (data) => {
        this.presentationTime = data;
      },
      (error) => {
        console.error('Error fetching presentation time:', error);
      }
    );
  }
  // -------------------------------------------------------------------------

  getAll(): Observable<any> {
    return this.httpClient.get(this.baseURL + '/room/reservation/v1?checkin=' + this.currentCheckInVal + '&checkout=' + this.currentCheckOutVal, { responseType: 'json' });
  }
}

// Interfaces and Classes

export interface Roomsearch {
  checkin: string;
  checkout: string;
}

// START OF CHANGES FOR PROBLEM 2
export interface Room {
  id: string;
  roomNumber: string;
  price: string;
  links: string;
  priceUSD?: string;
  priceCAD?: string;
  priceEUR?: string;
}
// END OF CHANGES FOR PROBLEM 2

export class ReserveRoomRequest {
  roomId: string;
  checkin: string;
  checkout: string;

  constructor(roomId: string,
              checkin: string,
              checkout: string) {

    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}

/*
// Commented out code
var ROOMS: Room[]=[
  {
  "id": "13932123",
  "roomNumber" : "409",
  "price" :"20",
  "links" : ""
},
{
  "id": "139324444",
  "roomNumber" : "509",
  "price" :"30",
  "links" : ""
},
{
  "id": "139324888",
  "roomNumber" : "609",
  "price" :"40",
  "links" : ""
}
]
*/
