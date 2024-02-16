package com.auto.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auto.in.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
