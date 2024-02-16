import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.css'
})
export class UpdateCarComponent {

  carId : number = this.activatedRoute.snapshot.params["id"];
  imgChanged :boolean= false ;
  selectedFile : any;
  imagePreview : string | ArrayBuffer | null;
  existingImage : string | null = null;
  updateForm !: FormGroup;
  listOfOption : Array<{ label : string , value : string }> = [];
  listOfBrands = ["Maruti Suzuki ","Hyundai","Tata","Mahindra","Kia","Toyota","Honda","Renualt","Volkswagen","Mg Motors","BMW","Audi"];
  listOfTypes  = ["Petrol" , "Hybrid" , "Diesel" , "Electric" , "CNG"];
  listOfColors = ["Blue","SIlver","Black","White","Red","Grey"];
  listOfTransmission = ["Automatic" , "Manual"]
  
  constructor(private adminService : AdminService ,
    private activatedRoute : ActivatedRoute,
    private fb: FormBuilder ,
    private router : Router){}

    ngOnInit(){
      this.updateForm = this.fb.group({
        name :[null , Validators.required],
        brand :[null , Validators.required],
        type :[null , Validators.required],
        color :[null , Validators.required],
        transmission :[null , Validators.required],
        price :[null , Validators.required],
        description :[null , Validators.required]
      })
      this.getCarById();
    }

    getCarById(){
      this.adminService.getCarById(this.carId).subscribe((resp)=>{
        const carDto = resp ;
        this.existingImage = 'data:image/jpeg;base64,'+resp.returnedImage;
        this.updateForm.patchValue(carDto);
      })
    }

    
  updateCar(){
    const formData : FormData = new FormData();
    if(this.imgChanged && this.selectedFile){
      formData.append('image' , this.selectedFile);
    }
    formData.append('brand' , this.updateForm.get('brand').value);
    formData.append('name' , this.updateForm.get('name').value);
    formData.append('type' , this.updateForm.get('type').value);
    formData.append('color' , this.updateForm.get('color').value);
    formData.append('transmission' , this.updateForm.get('transmission').value);
    formData.append('description' , this.updateForm.get('description').value);
    formData.append('price' , this.updateForm.get('price').value);
    console.log(formData);
      this.adminService.updateCar(this.carId ,formData).subscribe((resp) =>{
        console.log(resp);
        this.router.navigateByUrl('/admin/dashboard');
        alert("Car Details Updated Successfully");
      },error =>{
        console.log(error);
        alert("error while updating car!")
      })
    
  }

    onFileSelected(event:any){
      this.selectedFile = event.target.files[0];
      this.imgChanged = true;
      this.existingImage = null ;
      this.previewImage();
    }

    previewImage() {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      }
      reader.readAsDataURL(this.selectedFile);
    }
  

}
