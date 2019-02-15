package com.tj.thirstyCatWeb.drinks;

import java.util.Calendar;

public class Drink {

	private String id;
	private Calendar startDate;
	private Calendar endDate;
	
	//Default constructor for bean
	public Drink() {
		
	}
	
	public Drink(String id, Calendar startDate, Calendar endDate) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Drink [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
