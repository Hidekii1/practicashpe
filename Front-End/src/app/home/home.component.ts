import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  public currentUser: any;
  public loading = false;  // ← aquí defines la propiedad

  constructor() {
    const stored = localStorage.getItem('currentUser');
    this.currentUser = stored ? JSON.parse(stored) : '';
  }

  ngOnInit() {
    // Si vas a usar loading para controlar un spinner, 
    // podrías hacer algo así:
    this.loading = true;
    // …llamar a tu servicio…
    // al recibir la respuesta:
    // this.loading = false;
  }
}
