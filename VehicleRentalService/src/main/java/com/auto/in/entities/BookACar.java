package com.auto.in.entities;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.auto.in.dto.BookACarDto;
import com.auto.in.enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class BookACar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Date fromDate;
	
	private Date toDate;
	
	private long days ;
	
	private long price ;
	
	private BookCarStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "user_id" , nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "car_id" , nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Car car;
	
	public BookACarDto getBookACarDto() {
		BookACarDto bookACarDto = new BookACarDto();
		bookACarDto.setId(id);
		bookACarDto.setFromDate(fromDate);
		bookACarDto.setToDate(toDate);
		bookACarDto.setDays(days);
		bookACarDto.setPrice(price);
		bookACarDto.setStatus(status);
		bookACarDto.setEmail(user.getEmail());
		bookACarDto.setUsername(user.getName());
		bookACarDto.setUserId(user.getId());
		bookACarDto.setCarId(car.getId());
		
		return bookACarDto;
	}

}
