import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Book } from '../book';
import { BookService } from '../book.service';

@Component({
  selector: 'app-purchase-book',
  templateUrl: './purchase-book.component.html',
  styleUrls: ['./purchase-book.component.scss']
})
export class PurchaseBookComponent implements OnInit {
  purchaseId:any;
  bookId:any;
  book:any;
  purchaseForm:FormGroup;
  constructor(private activatedRoute:ActivatedRoute,
    private bookService:BookService) {
    this.activatedRoute.params.subscribe((parameters)=>{
      console.log(parameters);
      this.bookId = parameters['bookId'];
    })
    this.purchaseForm = new FormGroup({
      bookId : new FormControl(this.bookId,Validators.required),
      name : new FormControl("",[Validators.required]),
      email : new FormControl("",[Validators.required , Validators.email]),
    })
   }

  ngOnInit(): void {
  }
  purchase() {
    if(this.purchaseForm.invalid){
      throw new Error("Invalid form");
    }else{
      this.bookService.purchase(this.purchaseForm.value).subscribe({
        next : (res:any) => {
          console.log(res);
          this.purchaseId = res;
          this.bookService.getBookByMailandPID(this.purchaseForm.value.email , res).subscribe({
            next: (res:any) => {
              console.log(res);
              this.book = res[0];
            }
          })
        },
        error : (err:any) => {
          console.log("[PurchaseComponent][purchase] : ",err);
        }
      })
    }
  }

}

