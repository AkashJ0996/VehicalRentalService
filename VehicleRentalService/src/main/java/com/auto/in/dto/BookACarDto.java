package com.auto.in.dto;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.auto.in.entities.Car;
import com.auto.in.entities.User;
import com.auto.in.enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class BookACarDto {
    
	private long id;
	
	
	private Date fromDate;
	
	private Date toDate;
	
	private long days ;
	
	private long price ;
	
	private BookCarStatus status;
	
	private long userId;
	
	private long carId;
	
	private String username;
	
	private String email ;

}
