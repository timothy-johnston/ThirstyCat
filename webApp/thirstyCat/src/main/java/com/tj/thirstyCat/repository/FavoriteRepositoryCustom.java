package com.tj.thirstyCat.repository;

import java.util.List;

import com.tj.thirstyCat.model.Favorite;

public interface FavoriteRepositoryCustom {

	public List<Favorite> getFavoritesByUsername(String username);
	
}
