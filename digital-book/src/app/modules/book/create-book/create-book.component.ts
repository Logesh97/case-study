import { DatePipe, formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BookService } from '../book.service';

@Component({
  selector: 'app-create-book',
  templateUrl: './create-book.component.html',
  styleUrls: ['./create-book.component.scss']
})
export class CreateBookComponent implements OnInit {

  createBookForm:FormGroup;
  constructor(private bookService:BookService) {
    let authorName = JSON.parse(localStorage.getItem('login-status') || "")['sub'];
    this.createBookForm = new FormGroup({
      logo : new FormControl("" , Validators.required),
      title:new FormControl("" , Validators.required),
      category:new FormControl("" , Validators.required),
      price:new FormControl(25 , Validators.required),
      author:new FormControl(authorName , Validators.required),
      publisher:new FormControl("" , Validators.required),
      publishedDate:new FormControl(formatDate(Date.now() , 'yyyy-MM-dd','en') , Validators.required),
      content:new FormControl("" , Validators.required),
      active:new FormControl(true , Validators.required),
    });
   }

   createBook(){
      console.log(this.createBookForm.value);
      this.bookService.createBook(this.createBookForm.value).subscribe({
        next : (res:any) => {
            console.log(res);
        },
        error : (err:any) =>{
          console.log("");
        }
      });
   }

  ngOnInit(): void {
  }

}
