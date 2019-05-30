package com.tj.thirstyCat.model;

import java.io.File;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "drinks")
public class Drink extends AuditModel {

	@Id
	@GeneratedValue(generator = "drinkId_generator")
	@SequenceGenerator(name = "drinkId_generator", sequenceName = "drinkId_dequence", initialValue = 1)
	private Long id;
	
	@NotBlank
	private Date startDate;
	
	@NotBlank
	private Date endDate;
	
	@Lob
	@NotBlank
	private byte[] image;

	public Drink(@NotBlank Date startDate, @NotBlank Date endDate, @NotBlank byte[] image) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.image = image;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}
	
	
	
}
