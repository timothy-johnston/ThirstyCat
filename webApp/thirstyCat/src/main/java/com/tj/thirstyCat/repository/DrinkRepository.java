package com.tj.thirstyCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tj.thirstyCat.model.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
