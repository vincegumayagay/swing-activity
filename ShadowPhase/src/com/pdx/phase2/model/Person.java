package com.pdx.phase2.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Person {
	
	private static final AtomicInteger count = new AtomicInteger(0);
	private int id;
	private String lastName;
	private String firstName;
	private String gender;
	private LocalDate birthdate;
	
	public Person(String lastName, String firstName, String gender, LocalDate birthdate) {
		super();
		this.id = count.incrementAndGet();
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.birthdate = birthdate;
	}
	
	public int getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", gender=" + gender
				+ ", birthdate=" + birthdate + "]";
	}
	
	
	
}
