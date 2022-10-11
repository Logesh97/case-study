import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "./user.model";

@Injectable({
    providedIn:'root'
})
export class UserService{
    apiUrl:string = "http://localhost:9090/api/v1/digitalbooks/author";
    constructor(private http:HttpClient){
    }
    
    
    signup(user: User) {
        let signUpUrl = this.apiUrl+"/signup";
        return this.http.post(signUpUrl , user);
    }

    login(user:User){
        let loginUrl = this.apiUrl+"/login";
        return this.http.post(loginUrl , user);
    }
   
}