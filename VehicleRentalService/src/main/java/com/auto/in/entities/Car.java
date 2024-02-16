package com.auto.in.entities;

import com.auto.in.dto.CarDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="car")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id ;
	
	private String brand;
	
	private String color;
	
	private String name;
	
	private String type;
	
	private String transmission;
	
	private String description;
	
	private long price ;
	
	@Column(columnDefinition = "longblob")
	private byte[] image;
	
	public CarDto getCarDto() {
		CarDto dto = new CarDto();
		dto.setId(id);
		dto.setBrand(brand);
		dto.setColor(color);
		dto.setName(name);
		dto.setType(type);
		dto.setTransmission(transmission);
		dto.setDescription(description);
		dto.setPrice(price);
		dto.setReturnedImage(image);
		
		return dto;
	}

}
