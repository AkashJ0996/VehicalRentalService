import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

  cars : any = [];

  constructor(private adminService: AdminService){}

  ngOnInit(){
    this.getAllCars();
  }

  getAllCars(){
    this.adminService.getAllCar().subscribe((resp)=>{
      console.log(resp);
      resp.forEach(element => {
        console.log(element.returnedImage);
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImage ;
        this.cars.push(element);
   
      });
    })
  }

  deleteCar(id:number){
    console.log(id);
    this.adminService.deleteCar(id).subscribe((resp)=>{
      console.log(resp);
      this.getAllCars();
      window.location.reload();
      alert("Car Deleted successfully");
    })
 }

}
