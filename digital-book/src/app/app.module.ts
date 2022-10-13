import { SelectorMatcher } from '@angular/compiler';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { ButtonComponent } from './component/button/button.component';
import { HeaderComponent } from './component/header/header.component';
import { HomeComponent } from './component/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { PurchaseBookComponent } from './modules/book/purchase-book/purchase-book.component';
import { CreateBookComponent } from './modules/book/create-book/create-book.component';
import { MyBooksComponent } from './modules/book/my-books/my-books.component';
import { UserGuard } from './modules/user/user.guard';
import { WordCapitalize } from './word.capitalize';

const routes:Routes = [
  {path : 'home/:username' , component : HomeComponent},
  {path : 'purchase/:bookId' , component : PurchaseBookComponent},
  {path : 'create-book' , component : CreateBookComponent , canActivate:[UserGuard]},
  {path : 'my-book' , component : MyBooksComponent, canActivate:[UserGuard]},
  {path : 'home' , component : HomeComponent},
  {path : 'book' , loadChildren : () => import("./modules/book/book.module").then(m=>m.BookModule)},
  {path : 'user' , loadChildren : () => import("./modules/user/user.module").then(m=>m.UserModule)},
];
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ButtonComponent,
    HomeComponent,
    PurchaseBookComponent,
    CreateBookComponent,
    MyBooksComponent,
    WordCapitalize
    // SearchBookComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    ReactiveFormsModule    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
