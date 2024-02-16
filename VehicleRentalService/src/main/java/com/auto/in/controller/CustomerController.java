package com.auto.in.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auto.in.dto.BookACarDto;
import com.auto.in.dto.CarDto;
import com.auto.in.dto.ChangePasswordDto;
import com.auto.in.dto.SearchCarDto;
import com.auto.in.services.customer.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	
	
	@GetMapping("/cars")
	public ResponseEntity<List<CarDto>> getAllCars(){
		List<CarDto> allCars = customerService.getAllCars();
		return new ResponseEntity<List<CarDto>>(allCars , HttpStatus.OK);
	}
    
	@PostMapping("/car/book")
	public ResponseEntity<Void> bookACar(@RequestBody BookACarDto bookACarDto){
		boolean bookACar = customerService.bookACar(bookACarDto);
		if(bookACar) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
			
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/car/{id}")
	public ResponseEntity<CarDto> getCarByID(@PathVariable("id") long id){
		 CarDto carById = customerService.getCarById(id);
		if(carById == null ) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(carById);
	}
	
	@GetMapping("/car/bookings/{id}")
	public ResponseEntity<List<BookACarDto>> getBookingsByUserId(@PathVariable("id") long id){
		List<BookACarDto> allBookings = customerService.getBookingsByUserId(id);
		return new ResponseEntity<List<BookACarDto>>(allBookings , HttpStatus.OK);
	}
	
	@PostMapping("/car/search")
	public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto){
	    return ResponseEntity.ok(customerService.searchCar(searchCarDto));
	}
	
	
}
