package com.auto.in.services.customer;

import java.util.List;

import com.auto.in.dto.BookACarDto;
import com.auto.in.dto.CarDto;
import com.auto.in.dto.CarDtoListDto;
import com.auto.in.dto.ChangePasswordDto;
import com.auto.in.dto.SearchCarDto;
import com.auto.in.dto.UserDto;

public interface CustomerService {
	
	List<CarDto> getAllCars();
	
	boolean bookACar(BookACarDto bookACarDto);
	
	CarDto getCarById(long id);
	
	List<BookACarDto> getBookingsByUserId(long id);
	
	CarDtoListDto searchCar(SearchCarDto searchCarDto);
	
}
