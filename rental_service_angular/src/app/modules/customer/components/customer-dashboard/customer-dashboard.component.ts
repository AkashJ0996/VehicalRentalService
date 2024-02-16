import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.css'
})
export class CustomerDashboardComponent {

  cars : any = [];

  constructor(private customerService : CustomerService){}
  
  
  ngOnInit(){
    this.getAllCars();
  }

  getAllCars(){
    this.customerService.getAllCar().subscribe((resp)=>{
      console.log(resp);
      resp.forEach(element => {
        console.log(element.returnedImage);
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImage ;
        this.cars.push(element);
   
      });
    })
  }

}
