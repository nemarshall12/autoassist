package com.sommers.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sommers.demo.entities.Car;

@RepositoryRestResource(collectionResourceRel = "car", path = "car")
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

	
	
}
