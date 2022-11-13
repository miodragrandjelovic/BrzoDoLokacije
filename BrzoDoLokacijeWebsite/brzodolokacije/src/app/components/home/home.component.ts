import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
    var loggedIn = localStorage.getItem('loggedIn')
    if(loggedIn !== 'true'){
        this.router.navigate(['/login'])
    }
  }
  logout(){
    localStorage.removeItem('loggedIn');
    this.router.navigate(['/login']);
  }

  download(){
        let link = document.createElement('a');
        link.download = 'BrzoDoLokacije';
        link.href = 'assets/BrzoDoLokacije.apk';
        link.click();
  }
}
