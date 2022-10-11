import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Book } from '../book';
import { BookService } from '../book.service';

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrls: ['./my-books.component.scss']
})
export class MyBooksComponent implements OnInit {

  books:Book[] = [];
  editArea:boolean = false;
  editBookForm:FormGroup;
  constructor(private bookService:BookService) {
    this.getBookByAuhtor();
    this.editBookForm = this.initFormGroup();
   }
  initFormGroup(book:any = {}) {
    return new FormGroup({
      id: new FormControl(book.id || "" ),
      logo : new FormControl(book.logo || "" , Validators.required),
      title:new FormControl(book.title || "" , Validators.required),
      category:new FormControl(book.category || "" , Validators.required),
      price:new FormControl(book.price || 25 , Validators.required),
      author:new FormControl(book.author || "" , Validators.required),
      publisher:new FormControl(book.publisher || "" , Validators.required),
      publishedDate:new FormControl(formatDate(book.publishedDate || Date.now() , 'yyyy-MM-dd','en') , Validators.required),
      content:new FormControl(book.content || "" , Validators.required),
      active:new FormControl(book.active , Validators.required),
    });
  }
  getBookByAuhtor() {
    this.bookService.getBooksByAuthor().subscribe({
        next : (res:any) => {
          console.log(res);
          this.books = res;
        },
        error : (err:any) => {
          console.log("[MyBooksComponent][getBookByAuthor] error : ",err);
        }
    });
  }

  ngOnInit(): void {
  }
  editBook(book:Book){
    console.log(book);
    this.editBookForm = this.initFormGroup(book);
    this.editArea = true;
  }

   save(){
      console.log(this.editBookForm.value);
      this.bookService.editBook(this.editBookForm.value).subscribe({
        next : (res:any) => {
            console.log(res);
            alert("Changes saved successfully !!");
        },
        error : (err:any) =>{
          console.log("[MyBooksComponent][save] error : ", err);
        }
      });
   }
}
