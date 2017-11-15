package com.sommers.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.sommers.demo.entities.Car;
import com.sommers.demo.repositories.CarRepository;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {
 
    @Autowired
    CarRepository carRepository;
 
    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
    	List<Car> cars = new ArrayList<>();
    	cars.add(new Car("black", Float.valueOf("41995.00"), Float.valueOf("38795.00"), "Buick", "Enclave", false, 16720, 2017));
    	cars.add(new Car("black", Float.valueOf("27995.00"), Float.valueOf("24770.00"), "Acura", "TLX", false, 43752, 2015));
    	cars.add(new Car("red", Float.valueOf("18995.00"), Float.valueOf("16440.00"), "Buick", "Encore", false, 44219, 2015));
    	cars.add(new Car("silver", Float.valueOf("20995.00"), Float.valueOf("18795.00"), "Buick", "Encore", false, 19345, 2014));
    	cars.add(new Car("charcoal", Float.valueOf("35995.00"), Float.valueOf("30990.00"), "Buick", "Envision", false, 22704, 2017));
    	cars.add(new Car("white", Float.valueOf("60335.00"), Float.valueOf("58999.00"), "Buick", "Enclave", true, 0, 2018));
    	cars.add(new Car("gold", Float.valueOf("49245.00"), Float.valueOf("38946.00"), "Buick", "Enclave", true, 0, 2018));
    	carRepository.save(cars);
    	
    }
}
