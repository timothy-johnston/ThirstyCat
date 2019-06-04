package com.tj.thirstyCat.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "images")
public class Image {
	
	@Id
	@GeneratedValue(generator = "image_generator")
	@SequenceGenerator(name = "image_generator", sequenceName = "image_sequence", initialValue = 1)
	private Long id;
	private Long drinkId;
	
	@CreationTimestamp
	private Date createdDate;
	@Lob
	private byte[] imageByteArray;

	public Image() {
		
	}
	
	public Image(Long drinkId, byte[] imageByteArray) {
		this.drinkId = drinkId;
		this.imageByteArray = imageByteArray;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(Long drinkId) {
		this.drinkId = drinkId;
	}

	public byte[] getImageByteArray() {
		return imageByteArray;
	}

	public void setImageByteArray(byte[] imageByteArray) {
		this.imageByteArray = imageByteArray;
	}
	
}
