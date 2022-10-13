import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { SearchBookComponent } from './searchBook/searchBook.component';
import { HomeComponent } from 'src/app/component/home/home.component';
import { ButtonComponent } from 'src/app/component/button/button.component';
import { CreateBookComponent } from './create-book/create-book.component';
import { MyBooksComponent } from './my-books/my-books.component';
import { PurchasedBooksComponent } from './purchased-books/purchased-books.component';
import { BookReaderComponent } from './book-reader/book-reader.component';
import { WordCapitalize } from 'src/app/word.capitalize';

const routes:Routes = [
  {path:'searchBooks' , component : SearchBookComponent},
  {path:'purchasedBooks' , component : PurchasedBooksComponent},
  {path:'reader/:bookId' , component : BookReaderComponent},
];

@NgModule({
    declarations: [
      SearchBookComponent,
      PurchasedBooksComponent,
      BookReaderComponent,
      WordCapitalize
      // ButtonComponent,
      // HomeComponent
    ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
  ]
})
export class BookModule { }
