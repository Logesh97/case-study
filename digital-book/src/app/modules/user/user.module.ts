import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LogInComponent } from './log-in/log-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ReactiveFormsModule } from '@angular/forms';

const routes:Routes = [
  {path : 'logIn' , component : LogInComponent},
  {path : 'signUp' , component : SignUpComponent},
];

@NgModule({
  declarations: [
    LogInComponent,
    SignUpComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ]
})
export class UserModule { }
