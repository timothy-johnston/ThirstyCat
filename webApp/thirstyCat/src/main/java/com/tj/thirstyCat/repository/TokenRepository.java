package com.tj.thirstyCat.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tj.thirstyCat.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	@Transactional
	void deleteByIdIn(List<Long> ids);
	
}
