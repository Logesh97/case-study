import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
// import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'digital-book';
  // searchForm:FormGroup;
  
  constructor() {
    //  this.searchForm =  new FormGroup(
    //    {
    //       category : new FormControl("" , [Validators.required]) ,
    //       author : new FormControl("" , [Validators.required]),
    //       publisher : new FormControl("" , [Validators.required]),
    //       price : new FormControl(0 , [Validators.min(10)]),
    //    }
    //  );
   }


  // getBooks(){
  //   console.log(this.searchForm.value);
  // }
}
