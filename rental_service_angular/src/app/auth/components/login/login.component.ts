import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm : FormGroup
  falseLogged : boolean = false ;

  constructor(private fb:FormBuilder , private router : Router , private auth : AuthService){}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email : [null , [Validators.required , Validators.email]],
      password : [null , [Validators.required]],
    });
  }

  login(){
    this.auth.login(this.loginForm.value).subscribe((resp)=>{
     console.log(resp);
     if(resp.userId != null){
      const user ={
        id : resp.userId ,
        role : resp.userRole 
      }
      StorageService.saveUser(user);
      StorageService.saveToken(resp.jwt);
      if(StorageService.isAdminLoggedIn()){
          this.router.navigateByUrl("/admin/dashboard");
      }else if(StorageService.isCustomerLoggedIn()){
        this.router.navigateByUrl("/customer/dashboard");
      }else{
        this.falseLogged = true;
        this.loginForm.reset({});
      }
     }
    })
  }

  removemsg() {
    this.falseLogged = false;
    this.router.navigateByUrl('/login');
   }
}
