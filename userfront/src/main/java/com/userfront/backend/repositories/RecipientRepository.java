package com.userfront.backend.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.userfront.backend.domain.Recipient;

@Repository
public interface RecipientRepository extends CrudRepository<Recipient, Integer>{
	
	Set<Recipient> findAll();
	Recipient findByName(String name);
	void deleteByName(String name);

}
