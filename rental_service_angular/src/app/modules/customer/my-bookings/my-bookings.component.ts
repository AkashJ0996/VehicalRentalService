import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrl: './my-bookings.component.css'
})
export class MyBookingsComponent {
   
  bookings : any ;
  
  constructor(private customerService : CustomerService ,
    private activatedRoute : ActivatedRoute,
    private fb : FormBuilder,
    private router:Router){
    }

  ngOnInit(){
   this.getMyBookings();
  }

  getMyBookings(){
    this.customerService.getBookingsByUserId().subscribe((resp)=>{
      console.log(resp);
      this.bookings = resp;
    })
  }

}
