package com.tj.thirstyCat.repository;

import java.util.Optional;

import com.tj.thirstyCat.model.Image;

public interface ImageRepositoryCustom {

	public Image getImageByDrinkId(Long drinkId);
	
//	public Image getLastImage();
	
}
