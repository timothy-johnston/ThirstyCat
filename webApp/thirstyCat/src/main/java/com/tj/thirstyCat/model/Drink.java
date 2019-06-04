package com.tj.thirstyCat.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "drinks")
public class Drink {
	
	@Id
	@GeneratedValue(generator = "drink_generator")
	@SequenceGenerator(name = "drink_generator", sequenceName = "drink_sequence", initialValue = 1)
	private Long id;
	private Date startTime;
	private Date endTime;
	
	public Drink() {
		
	}
	
	public Drink(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

}
