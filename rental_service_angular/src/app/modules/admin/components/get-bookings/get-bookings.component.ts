import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-get-bookings',
  templateUrl: './get-bookings.component.html',
  styleUrl: './get-bookings.component.css'
})
export class GetBookingsComponent {
  
  bookings :any ;

  constructor(private adminService:AdminService){}

  ngOnInit(){
    this.getBookings();
  }

  getBookings(){
    this.adminService.getBookings().subscribe((resp)=>{
      console.log(resp);
      this.bookings = resp;
    })
  }

  changeBookingStatus(bookingId : number , status : string){
    this.adminService.changeBookingStatus(bookingId,status).subscribe((resp)=>{
    console.log(resp);
    this.getBookings();
    alert("Booking Status Updated Successfully")
  },(error)=>{
    console.log(error);
    alert("Something went WRONG..")
  })
  

  }
}
