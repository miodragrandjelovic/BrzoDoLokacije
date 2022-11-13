import { Component, OnInit } from '@angular/core';
import { FormGroup,FormBuilder,Validators} from '@angular/forms';
import { Router } from '@angular/router';
@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {
  loginForm:FormGroup;
  loading = false;
  submitted = false;
  returnUrl:String="";
  constructor(private formBuilder: FormBuilder,
    private router: Router,
    ) { 
      this.loginForm = this.formBuilder.group({
        email:["",Validators.required],
        password:["",Validators.required]
      })
    }

  ngOnInit(): void {
    var loggedIn = localStorage.getItem('loggedIn');
    if(loggedIn === 'true'){
        this.router.navigate(['/home'])
    }
  }

  get fval(){
    return this.loginForm.controls;
  }

  onFormSubmit()
  {
    if(this.fval['email'].value !== ""){
      if(this.fval['password'].value == "pyxiscapri1!"){
        localStorage.setItem('loggedIn','true');
        this.router.navigate(['/home']);
      }
      else {
        alert('Pogresna sifra')
        this.router.navigate(['/login']);
      }
    }
    else{
      alert("Morate uneti email!")
    }
    
  }
}