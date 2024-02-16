package com.auto.in.services.admin;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.auto.in.dto.BookACarDto;
import com.auto.in.dto.CarDto;
import com.auto.in.dto.CarDtoListDto;
import com.auto.in.dto.SearchCarDto;
import com.auto.in.entities.BookACar;
import com.auto.in.entities.Car;
import com.auto.in.enums.BookCarStatus;
import com.auto.in.repository.BookACarRepository;
import com.auto.in.repository.CarRepository;

import io.jsonwebtoken.io.IOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private final CarRepository carRepository;
	
	private final BookACarRepository bookACarRepository;

	@Override
	public boolean postCar(CarDto carDto)throws IOException{
		try {
		Car car = new Car();
		car.setBrand(carDto.getBrand());
		car.setColor(carDto.getColor());
		car.setName(carDto.getName());
		car.setPrice(carDto.getPrice());
		car.setType(carDto.getType());
		car.setTransmission(carDto.getTransmission());
		car.setDescription(carDto.getDescription());
		  // Check if image is not null before accessing its bytes
        if (carDto.getImage() != null) {
            car.setImage(carDto.getImage().getBytes());
        }
		
		
		carRepository.save(car);
		return true;
		}catch (Exception e) {
			System.out.println(e);
			return false;
		}
	
	}

	@Override
	public List<CarDto> getAllCars() {
		// TODO Auto-generated method stub
		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public void deleteCar(long id) {
	  carRepository.deleteById(id);
	}

	@Override
	public CarDto getCarById(long id) {
		Optional<Car> optionalCar = carRepository.findById(id);
		return optionalCar.map(Car::getCarDto).orElse(null);
	}

	@Override
	public boolean updateCar(long carId, CarDto carDto) throws java.io.IOException {
		Optional<Car> optionalCar = carRepository.findById(carId);
		if(optionalCar.isPresent()){
			Car existingCar = optionalCar.get();
			if (carDto.getImage() != null) 
	            existingCar.setImage(carDto.getImage().getBytes());
            existingCar.setBrand(carDto.getBrand());
            existingCar.setColor(carDto.getColor());
            existingCar.setName(carDto.getName());
            existingCar.setPrice(carDto.getPrice());
            existingCar.setType(carDto.getType());
            existingCar.setTransmission(carDto.getTransmission());
            existingCar.setDescription(carDto.getDescription());
	            
            carRepository.save(existingCar);
            return true;
	        
		}else {
			return false;
		}
	}

	@Override
	public List<BookACarDto> getBookings() {
		
		return bookACarRepository.findAll().stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
	}

	@Override
	public boolean changeBookingStatus(long bookingId, String status) {
		Optional<BookACar> optionalBookACar = bookACarRepository.findById(bookingId);
		if(optionalBookACar.isPresent()) {
			BookACar existingBookACar = optionalBookACar.get();
			if(Objects.equals(status,"Approve"))
				existingBookACar.setStatus(BookCarStatus.APPROVED);
			else
				existingBookACar.setStatus(BookCarStatus.REJECTED);
			bookACarRepository.save(existingBookACar);
			return true;
		}
		return false;
	}

	
	@Override
	public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
	    Root<Car> root = criteriaQuery.from(Car.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (searchCarDto.getBrand() != null && !searchCarDto.getBrand().isEmpty()) {
	        predicates.add(criteriaBuilder.like(root.get("brand"), "%" + searchCarDto.getBrand() + "%"));
	    }
	    if (searchCarDto.getType() != null && !searchCarDto.getType().isEmpty()) {
	        predicates.add(criteriaBuilder.like(root.get("type"), "%" + searchCarDto.getType() + "%"));
	    }
	    if (searchCarDto.getTransmission() != null && !searchCarDto.getTransmission().isEmpty()) {
	        predicates.add(criteriaBuilder.like(root.get("transmission"), "%" + searchCarDto.getTransmission() + "%"));
	    }
	    if (searchCarDto.getColor() != null && !searchCarDto.getColor().isEmpty()) {
	        predicates.add(criteriaBuilder.like(root.get("color"), "%" + searchCarDto.getColor() + "%"));
	    }

	    criteriaQuery.where(predicates.toArray(new Predicate[0]));

	    TypedQuery<Car> query = entityManager.createQuery(criteriaQuery);
	    List<Car> carList = query.getResultList();

	    CarDtoListDto carDtoListDto = new CarDtoListDto();
	    carDtoListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));

	    return carDtoListDto;
	}



}
