package com.auto.in.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auto.in.dto.CarDto;
import com.auto.in.entities.BookACar;

@Repository
public interface BookACarRepository extends JpaRepository<BookACar, Long> {

	List<BookACar> findAllByUserId(long userId);

}
