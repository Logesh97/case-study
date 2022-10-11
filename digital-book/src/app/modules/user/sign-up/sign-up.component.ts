import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

  signUpForm:FormGroup;
  constructor(private userService:UserService , 
    private router:Router) {
    this.signUpForm = new FormGroup({
      username : new FormControl("",Validators.required),
      password : new FormControl("",[Validators.required , Validators.maxLength(8)]),
      mailId : new FormControl("",[Validators.required ]),
    })
   }

  ngOnInit(): void {
  }

  signup(){
    console.log(this.signUpForm.value);
    // let decodeValue ;
    // if(this.signUpForm.invalid){
    //   throw new Error();
    //   alert("Enter valid input");
    // }else{
      
      this.userService.signup(this.signUpForm.value).subscribe({
        next : (res:any) => {
            console.log(res);
            this.router.navigate(["user","logIn"]);
        },
        error : (err:any) => {
          console.log("Error [LogInComponent][login] : ",err);
        }
      })
    }
  // }

}
