package com.auto.in.services.customer;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auto.in.dto.BookACarDto;
import com.auto.in.dto.CarDto;
import com.auto.in.dto.CarDtoListDto;
import com.auto.in.dto.ChangePasswordDto;
import com.auto.in.dto.SearchCarDto;
import com.auto.in.dto.UserDto;
import com.auto.in.entities.BookACar;
import com.auto.in.entities.Car;
import com.auto.in.entities.User;
import com.auto.in.enums.BookCarStatus;
import com.auto.in.repository.BookACarRepository;
import com.auto.in.repository.CarRepository;
import com.auto.in.repository.UserRepository;

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
public class CustomerServiceImpl implements CustomerService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private final CarRepository carRepository ;
	
	private final UserRepository userRepository ;
	
	private final BookACarRepository bookACarRepository  ;
	

	@Override
	public List<CarDto> getAllCars() {
		 return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
		
	}

	@Override
	public CarDto getCarById(long id) {
		Optional<Car> optionalCar = carRepository.findById(id);
		return optionalCar.map(Car::getCarDto).orElse(null);
	}


	@Override
	public List<BookACarDto> getBookingsByUserId(long id) {
		return bookACarRepository.findAllByUserId(id).stream().map(BookACar :: getBookACarDto).collect(Collectors.toList());
	}




	@Override
	public boolean bookACar(BookACarDto bookACarDto) {
		Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
		Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());
		
		if(optionalCar.isPresent() && optionalUser.isPresent()) {
			Car existingCar = optionalCar.get();
			BookACar bookACar = new BookACar();
			bookACar.setUser(optionalUser.get());
			bookACar.setCar(existingCar);
			bookACar.setStatus(BookCarStatus.PENDING);
			long diffInMiliSeconds = bookACarDto.getToDate().getTime()-bookACarDto.getFromDate().getTime();
			long days = TimeUnit.MILLISECONDS.toDays(diffInMiliSeconds);
			bookACar.setDays(days);
			bookACar.setToDate(bookACarDto.getToDate());
			bookACar.setFromDate(bookACarDto.getFromDate());
			bookACar.setPrice(existingCar.getPrice()* days);
			bookACarRepository.save(bookACar);
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
