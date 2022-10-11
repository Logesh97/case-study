import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../user.service';
import jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';
import { HeaderComponent } from 'src/app/component/header/header.component';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.scss'],
  providers:[HeaderComponent]
})
export class LogInComponent implements OnInit {

  loginForm:FormGroup;
  constructor(private userService:UserService , private router : Router
    , private header:HeaderComponent) { 
    this.loginForm = new FormGroup({
      username : new FormControl("",Validators.required),
      password : new FormControl("",[Validators.required , Validators.minLength(8)])
    })
  }

  login(){
    console.log(this.loginForm.value);
    let decodeValue ;
      this.userService.login(this.loginForm.value).subscribe({
        next : (res:any) => {
            console.log(res);
            decodeValue = jwt_decode(res['token']);
            console.log("Decoded token : ",decodeValue);
            localStorage.setItem("token" , res['token']);
            localStorage.setItem("login-status" , JSON.stringify(decodeValue));
            this.header.authorMode = true;
            if(JSON.parse(localStorage.getItem("login-status") || "")['sub']){
              this.router.navigate(["home",JSON.parse(localStorage.getItem("login-status") || "")['sub']]);
            }
        },
        error : (err:any) => {
          console.log("Error [LogInComponent][login] : ",err);
        }
      })
  }
  ngOnInit(): void {
  }

}
