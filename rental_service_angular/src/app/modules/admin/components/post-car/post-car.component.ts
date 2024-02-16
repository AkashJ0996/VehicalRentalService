import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-car',
  templateUrl: './post-car.component.html',
  styleUrl: './post-car.component.css'
})
export class PostCarComponent {

  postVForm !: FormGroup ;
  // posted : boolean = false;

  selectedFile : File | null ;
  imagePreview :String | ArrayBuffer | null ;
  listOfOption : Array<{ label : string , value : string }> = [];
  listOfBrands = ["Maruti Suzuki ","Hyundai","Tata","Mahindra","Kia","Toyota","Honda","Renualt","Volkswagen","Mg Motors","BMW","Audi"];
  listOfTypes  = ["Petrol" , "Hybrid" , "Diesel" , "Electric" , "CNG"];
  listOfColors = ["Blue","SIlver","Black","White","Red","Grey"];
  listOfTransmission = ["Automatic" , "Manual"];

  constructor(private fb : FormBuilder , 
    private adminService : AdminService , 
    private router : Router){}

  ngOnInit(){
    this.postVForm = this.fb.group({
      name :[null , Validators.required],
      brand :[null , Validators.required],
      type :[null , Validators.required],
      color :[null , Validators.required],
      transmission :[null , Validators.required],
      price :[null , Validators.required],
      description :[null , Validators.required]
    })
  }

  postVehicle(){
    const formData : FormData = new FormData();
    formData.append('image' , this.selectedFile);
    formData.append('brand' , this.postVForm.get('brand').value);
    formData.append('name' , this.postVForm.get('name').value);
    formData.append('type' , this.postVForm.get('type').value);
    formData.append('color' , this.postVForm.get('color').value);
    formData.append('transmission' , this.postVForm.get('transmission').value);
    formData.append('description' , this.postVForm.get('description').value);
    formData.append('price' , this.postVForm.get('price').value);
    console.log(formData);
      this.adminService.postCar(formData).subscribe((resp) =>{
        console.log(resp);
        this.router.navigateByUrl('/admin/dashboard');
        // if(resp != null){
        //   this.posted = true;
        //   this.postVForm.reset({});
        // } 
      },error =>{
        console.log(error);
      })
    
  }


  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }
  previewImage() {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }

  // removemsg() {
  //  this.posted = false;
  //  this.router.navigateByUrl('/admin/dashboard');
  // }


}
