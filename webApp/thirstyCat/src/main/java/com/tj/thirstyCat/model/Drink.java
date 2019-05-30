package com.tj.thirstyCat.model;

import java.io.File;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
	@NotBlank
	private File image;
	
}
