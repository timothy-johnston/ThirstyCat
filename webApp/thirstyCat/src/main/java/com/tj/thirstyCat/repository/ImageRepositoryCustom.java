package com.tj.thirstyCat.repository;

import com.tj.thirstyCat.model.Image;

public interface ImageRepositoryCustom {

	public Image getImageByDrinkId(Long drinkId);
	
}
