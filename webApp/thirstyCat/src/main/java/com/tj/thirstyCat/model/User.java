package com.tj.thirstyCat.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(generator = "image_generator")
	@SequenceGenerator(name = "image_generator", sequenceName = "image_sequence", initialValue = 1)
	private Long id;
	
	@CreationTimestamp
	private Date createdDate;
	
	private String createdBy;
	private String userName;
	private String password;

}
