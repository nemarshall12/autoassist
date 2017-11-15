package com.sommers.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String color;

	private Float msrp;

	private Float sommersPrice;

	private String make;

	private String model;

	private boolean newCar;

	private Integer miles;

	private Integer year;

	
	public Car() {
		
	}
	
	public Car(String color, Float msrp, Float sommersPrice, String make, String model, boolean newCar, Integer miles,
			Integer year) {
		this.color = color;
		this.msrp = msrp;
		this.sommersPrice = sommersPrice;
		this.make = make;
		this.model = model;
		this.newCar = newCar;
		this.miles = miles;
		this.year = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Float getMsrp() {
		return msrp;
	}

	public void setMsrp(Float msrp) {
		this.msrp = msrp;
	}

	public Float getSommersPrice() {
		return sommersPrice;
	}

	public void setSommersPrice(Float sommersPrice) {
		this.sommersPrice = sommersPrice;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getMiles() {
		return miles;
	}

	public void setMiles(Integer miles) {
		this.miles = miles;
	}

	public boolean isNewCar() {
		return newCar;
	}

	public void setNewCar(boolean newCar) {
		this.newCar = newCar;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
