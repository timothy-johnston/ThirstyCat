package com.tj.thirstyCat.repository;

import java.util.Optional;

public interface ImageRepositoryCustom {

	public Optional<byte[]> getImageByDrinkId(Long drinkId);
	
	public byte[] getLastImage();
	
}
