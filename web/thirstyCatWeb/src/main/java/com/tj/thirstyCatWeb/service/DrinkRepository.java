package com.tj.thirstyCatWeb.service;

import com.tj.thirstyCatWeb.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
