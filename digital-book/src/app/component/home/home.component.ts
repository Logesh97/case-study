import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from 'src/app/modules/book/book';
import { BookService } from 'src/app/modules/book/book.service';
import { UserModule } from 'src/app/modules/user/user.module';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  username:string = "Reader";

  @Input()
  books:Book[] = [];

  constructor(private bookService:BookService,private activatedRoute:ActivatedRoute,
    private router:Router) { 
    this.getAllBooks();
    this.activatedRoute.params.subscribe((parameters)=>{
      console.log(parameters);
      this.username = parameters['username'];
    })
  }

  purchaseBook(book:Book){
    this.router.navigate(["purchase",book.id]);
  }


  getAllBooks(){
    this.bookService.getAllBooks().subscribe({
      next: (res:any) => {
        this.books = res;
      },
      error: (err:any) => {
        console.log('Error [HomeComponent][getAllBooks] : ', err);
      }
    });
  }

}
