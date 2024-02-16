import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  signupForm : FormGroup ;
  created : boolean = false;

  constructor(private fb : FormBuilder , private authService :AuthService , private router : Router){
  }
  
  ngOnInit(): void {
    this.signupForm = this.fb.group({
      name : [null , [Validators.required]],
      email : [null , [Validators.required , Validators.email]],
      password : [null , [Validators.required]],
      checkPassword : [null , [Validators.required , this.passwordValidation]],
      mark: [null , [Validators.required , this.markValidation]]
    });
  }

  passwordValidation = (control : FormControl) : { [s: string]: boolean } =>{
    if(!control.value){
      return{ required: true };
    }else if (control.value !== this.signupForm.controls['password'].value){
      return { confirm: true , error : true}
    }
    return {};
  };

  markValidation = (control : FormControl) : { [s: string]: boolean } =>{
    if(!control.value){
      return{ required: true };
    }
    return {};
  };

  register(){
    return this.authService.register(this.signupForm.value).subscribe((resp)=>{
       console.log(resp);
       if(resp && resp.id != null){
        this.created = true;
        this.signupForm.reset({});
       }
       
       
    },(error)=>{
      console.log(error);
    });
  }

  removemsg() {
   this.created = false;
   this.router.navigateByUrl('/login');
  }
}
