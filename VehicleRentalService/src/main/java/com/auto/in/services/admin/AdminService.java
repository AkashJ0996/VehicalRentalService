package com.auto.in.services.admin;

import java.util.List;

import com.auto.in.dto.BookACarDto;
import com.auto.in.dto.CarDto;
import com.auto.in.dto.CarDtoListDto;
import com.auto.in.dto.SearchCarDto;

import io.jsonwebtoken.io.IOException;

public interface AdminService {
	
	boolean postCar(CarDto carDto)throws IOException;
   
	List<CarDto> getAllCars();
	
	void deleteCar(long id);
	
	CarDto getCarById(long id);
	
	boolean updateCar(long carId , CarDto carDto) throws java.io.IOException ;
	
	List<BookACarDto> getBookings();
	
	boolean changeBookingStatus(long bookingId , String status );
	
	CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
