package com.auto.in.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auto.in.dto.BookACarDto;
import com.auto.in.dto.CarDto;
import com.auto.in.dto.CarDtoListDto;
import com.auto.in.dto.SearchCarDto;
import com.auto.in.services.admin.AdminService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;
	
	@PostMapping("/car")
	public ResponseEntity<?> postCars(@ModelAttribute CarDto carDto)throws IOException{
		boolean posted = adminService.postCar(carDto);
		if(posted) {
		   return ResponseEntity.status(HttpStatus.CREATED).build();
		}else {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/cars")
	public ResponseEntity<?> getAllCars(){
		List<CarDto> allCars = adminService.getAllCars();
		return new ResponseEntity<>(allCars,HttpStatus.OK);
	}
	
	@DeleteMapping("/car/{id}")
	public ResponseEntity<Void> deleteCars(@PathVariable("id") long id){
		 adminService.deleteCar(id);
		 return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/car/{id}")
	public ResponseEntity<CarDto> getCarById(@PathVariable("id") long id){
		 CarDto carById = adminService.getCarById(id);
		 return new ResponseEntity<>(carById,HttpStatus.OK);
	}
	
	@PutMapping("/car/{id}")
	public ResponseEntity<Void> updateCar(@PathVariable("id") long id ,@ModelAttribute CarDto carDto) throws java.io.IOException{
		try{
			boolean updateCar = adminService.updateCar(id, carDto);
		if(updateCar) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@GetMapping("/car/bookings")
	public ResponseEntity<List<BookACarDto>> getBookings(){
		List<BookACarDto> allBookings = adminService.getBookings();
		return new ResponseEntity<List<BookACarDto>>(allBookings,HttpStatus.OK);
	}
	
	@GetMapping("/car/booking/{id}/{status}")
	public ResponseEntity<?> changeBookingStatus(@PathVariable("id") long id,@PathVariable("status") String status){
		boolean success = adminService.changeBookingStatus(id, status);
		if(success)return ResponseEntity.status(HttpStatus.OK).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PostMapping("/car/search")
	public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto){
	    return ResponseEntity.ok(adminService.searchCar(searchCarDto));
	}

}
