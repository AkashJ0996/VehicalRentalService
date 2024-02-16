import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { AdminService } from '../../../admin/services/admin.service';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrl: './search-car.component.css'
})
export class SearchCarComponent {

  
  cars : any = [];

  processedImage : any;
  searchCarForm !: FormGroup;
  listOfOption : Array<{ label : string , value : string }> = [];
  listOfBrands = ["Maruti Suzuki ","Hyundai","Tata","Mahindra","Kia","Toyota","Honda","Renualt","Volkswagen","Mg Motors","BMW","Audi"];
  listOfTypes  = ["Petrol" , "Hybrid" , "Diesel" , "Electric" , "CNG"];
  listOfColors = ["Blue","SIlver","Black","White","Red","Grey"];
  listOfTransmission = ["Automatic" , "Manual"];

  constructor(private customerService : CustomerService ,
    private fb: FormBuilder ){
      this.searchCarForm = this.fb.group({
        brand :[null ],
        type :[null ],
        transmission :[null],
        color :[null ],
        
      })
    }

    searchCar(){
      console.log(this.searchCarForm.value);
      this.customerService.searchCar(this.searchCarForm.value).subscribe((resp)=>{
         resp.carDtoList.forEach(element => {
          element.processedImage = 'data:image/jpeg;base64,' + element.returnedImage ;
          this.cars.push(element);
      
        });
  
       })
    }
}
