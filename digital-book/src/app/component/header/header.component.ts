import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AppComponent } from "src/app/app.component";

@Component({
    selector : 'app-header',
    templateUrl : './header.component.html',
    styleUrls : ['./header.component.scss'],
    providers:[AppComponent]
})
export class HeaderComponent implements OnInit{
    title:string = "digital book";

    loginStatus:any;
    authorMode:boolean;
    constructor(private router:Router , private appComponent:AppComponent){
        this.loginStatus = localStorage.getItem("login-status")
        this.authorMode = this.loginStatus == null
        console.log("Author mode",this.authorMode);

    }
    ngOnInit(): void {
        //throw new Error("Method not implemented.");
    }
    setAuthorMode(isAuthor:boolean){
        this.authorMode = isAuthor;
    }
    signout(){
        localStorage.removeItem("login-status");
        localStorage.removeItem("token");
        this.authorMode = true;
        // window.location.reload();
        // this.authorMode = false;
    }
}