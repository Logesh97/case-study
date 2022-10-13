import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Book } from '../book';
import { BookService } from '../book.service';

@Component({
  selector: 'app-purchased-books',
  templateUrl: './purchased-books.component.html',
  styleUrls: ['./purchased-books.component.scss']
})
export class PurchasedBooksComponent implements OnInit {

  isMailArea:boolean = false;
  books:Book[] = [];
  mailId:string = this.getMail() || "";
  constructor(private bookService:BookService, private router:Router) { 
    if(this.mailId != null && this.mailId !== ""){
      this.setMailAndLoad();
    }
  }

  ngOnInit(): void {
  }
  toggle(param:any){
    this.isMailArea = !this.isMailArea;
    console.log(param);
  }

  storeMail(mail:string){
    localStorage.setItem('reader_mail',mail);  
    this.setMailAndLoad();
  }
  setMailAndLoad(){
    let mail =this.getMail() || "";
    this.bookService.fetchPurchasedBooks(mail).subscribe({
      next:(res:any) => {
          this.books = res;
          console.log(res);
      },
      error:(err:any) => {
        console.log("[PurchasedBooksComponent][storeMail] error : ",err);
      }
    });
  }
  getMail(){
    return localStorage.getItem('reader_mail') || null;
  }

  refund(book:Book){
    let mail =this.getMail();
    if(mail === null){
      alert('please register mail id..');
      this.isMailArea = false;
    }else{
      this.bookService.refund(mail , book.id).subscribe({
        next:(res:any) => {
          // this.books = res;
          if(res.result !== 'success'){
            alert('Refunded!!');
            this.setMailAndLoad();
          }else{
            alert(res.result);
          }
          console.log(res);
        },
        error:(err:any) => {
          console.log("[PurchasedBooksComponent][refund] error : ",err);
        }
      });
  }
}
  read(book:Book){
    alert("read book");
    this.router.navigate(['book','reader',book.id]);
  }

  findByPurchaseId(pid:any){
    let mail =this.getMail();
    if(mail === null){
      alert('please register mail id..');
      this.isMailArea = false;
    }else{
      this.bookService.getBookByMailandPID(mail , pid).subscribe({
        next:(res:any) => {
          this.books = res;
          console.log(res);
        },
        error:(err:any) => {
          console.log("[PurchasedBooksComponent][findByPurchaseId] error : ",err);
        }
      })

    }
  }

}
