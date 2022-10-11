import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Book } from '../book';
import { BookService } from '../book.service';

@Component({
  selector: 'app-search.book',
  templateUrl: './searchBook.component.html',
  styleUrls: ['./searchBook.component.scss']
})
export class SearchBookComponent {

  searchForm:FormGroup;
  books:Book[] = [];

  constructor(private bookService : BookService , private router:Router) {
     this.searchForm =  new FormGroup(
       {
          category : new FormControl("" , [Validators.required]) ,
          author : new FormControl("" , [Validators.required]),
          publisher : new FormControl("" , [Validators.required]),
          price : new FormControl(0 , [Validators.min(10)]),
       }
     );
   }

   purchaseBook(book:Book){
     this.router.navigate(["purchase" , book.id]);
   }

  getBooks(){
    console.log(this.searchForm.value);
    this.bookService.getBooksByParam(this.searchForm.value).subscribe({
      next : (res:any) => {
        console.log(res);
        this.books = res;
      },
      error : (err:any) => {
          console.log("Errorr.............");
          console.log(err);
      }
    })
  }


}
