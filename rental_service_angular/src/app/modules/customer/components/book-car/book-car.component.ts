import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrl: './book-car.component.css'
})
export class BookCarComponent {
 
  
  carId : number = this.activatedRoute.snapshot.params["id"];
  car : any ;
  processedImage : any;
  validateForm !: FormGroup ;
  toDate : Date ;
  fromDate: Date;

  constructor(private customerService : CustomerService ,
    private activatedRoute : ActivatedRoute,
    private fb : FormBuilder,
    private router:Router){
    }

  ngOnInit(){
    this.validateForm = this.fb.group({
      toDate : [null , Validators.required],
      fromDate : [null , Validators.required]
    }),
   this.getCarById();
  
  }

  getCarById(){
    this.customerService.getCarById(this.carId).subscribe((resp)=>{
      console.log(resp);
      this.processedImage = 'data:image/jpeg;base64,' + resp.returnedImage ;
      this.car = resp;
    })
  }

  bookACar(data : any){
      console.log(data);
      const toDate = this.validateForm.get('toDate')?.value; // Get toDate from form control
      const fromDate = this.validateForm.get('fromDate')?.value; // Get fromDate from form control
      let bookACarDto = {
        toDate:new Date(toDate).getTime(),
        fromDate:new Date(fromDate).getTime(),
        userId : StorageService.getUserId(),
        carId : this.carId
      }
      console.log(bookACarDto);
      this.customerService.bookACar(bookACarDto).subscribe((resp)=>{
        console.log(resp);
        alert("Booking Request Submitted Successfully ...");
        this.router.navigateByUrl("customer/dashboard");
      },(error)=>{
        console.log(error)
        alert("Something went wrong!!...");
      })
   
  }
  


}   

