import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookService } from '../book.service';

@Component({
  selector: 'app-book-reader',
  templateUrl: './book-reader.component.html',
  styleUrls: ['./book-reader.component.scss'],
})
export class BookReaderComponent implements OnInit {

  book:any;
  constructor(private bookService:BookService,
    private activatedRoute:ActivatedRoute) {
      this.activatedRoute.params.subscribe((parameters)=>{
        console.log(parameters);
        this.getBook(parameters['bookId']);
      })
  }

  getBook(bookId:number){
    let mailId = localStorage.getItem('reader_mail') || "";
    this.bookService.getBookToRead(mailId , bookId).subscribe({
      next : (res:any) => {
        if(res[0] !== null){
          console.log(res[0]);
          this.book = res[0];
        }else{
          alert("No books to read!!");
        }
      }
    })

  }
  ngOnInit(): void {
  }

}
