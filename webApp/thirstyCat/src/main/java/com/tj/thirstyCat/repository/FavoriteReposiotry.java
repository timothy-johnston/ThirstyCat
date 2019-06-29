package com.tj.thirstyCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tj.thirstyCat.model.Favorite;

@Repository
public interface FavoriteReposiotry extends JpaRepository<Favorite, Long>, FavoriteRepositoryCustom {

}
