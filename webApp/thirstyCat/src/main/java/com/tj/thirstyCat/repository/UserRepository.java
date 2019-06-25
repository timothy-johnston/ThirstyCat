package com.tj.thirstyCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tj.thirstyCat.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}