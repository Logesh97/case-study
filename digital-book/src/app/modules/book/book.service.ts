import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Book } from "./book";
import { Purchase } from "./purchase-book/purchase.model";

@Injectable({
    providedIn:'root'
})
export class BookService{
    
    
    
    apiUrl:string = "http://localhost:9090/api/v1/digitalbooks/books";
    readerUrl:string = "http://localhost:9090/api/v1/digitalbooks/readers";
    authorUrl:string = "http://localhost:9090/api/v1/digitalbooks/author";
    // httpParams = ;
    constructor(private http:HttpClient){
    }

    getBookToRead(mailId: string , bookId: number) {
        // /{mailId}/books/{bookId}
        let url = this.readerUrl+"/"+mailId+"/books/"+bookId;
        return this.http.get(url);
    }
    
    refund(mail: string, id: number) {
        // /{mailId}/books/{bookId}/refund
        let url = this.readerUrl+"/"+mail+"/books/"+id+"/refund";
        return this.http.post(url,{});
    }
    fetchPurchasedBooks(mail: string) {
        let url = this.readerUrl+"/"+mail+"/books";
        return this.http.get(url);   
    }

    editBook(book  : Book) {
        // /{authorId}/books/{bookId}
        console.log("edited book : ",book);
        let url = this.authorUrl+"/"+JSON.parse(localStorage.getItem('login-status') || "")['authorId']+"/books/"+book.id;
      return this.http.put(url,book, {headers : {
        "Authorization" : "Bearer "+ localStorage.getItem('token')
      }});
    }
    
    getBooksByAuthor() {
      let url = this.apiUrl+"/fetchByAuthor?authorId="+JSON.parse(localStorage.getItem('login-status') || "")['sub'];
      return this.http.get(url);
    }
    createBook(book: Book) {
      let url = this.authorUrl+"/"+JSON.parse(localStorage.getItem('login-status') || "")['authorId']+"/books";
      return this.http.post(url,book, {headers : {
        "Authorization" : "Bearer "+ localStorage.getItem('token')
      }});
    }
    getBooksByParam(param:any){
        let searchUrl = this.apiUrl+'/search?category='+param['category']+'&author='
        +param['author']+'&price='+param['price']+'&publisher='+param['publisher'];
        // this.httpParams.append("category" , param['category']);
        // this.httpParams.append("author" , param['author']);
        // this.httpParams.append("price" , param['price']);
        // this.httpParams.append("publisher" , param['publisher']);
        return this.http.get(searchUrl);
    }
    getAllBooks() {
      return this.http.get(this.apiUrl);
    }

    purchase(purchase: Purchase) {
        let purchaseUrl = this.apiUrl+"/buy";
        return this.http.post(purchaseUrl,purchase);
    }

    getBookByMailandPID(mail:string,pid:number){
        let body = {"payment_id":pid};
        let url = this.readerUrl+"/"+mail+"/books";
        return this.http.post(url,body);
    }
}