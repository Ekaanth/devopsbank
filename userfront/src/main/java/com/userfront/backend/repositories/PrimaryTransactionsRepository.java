package com.userfront.backend.repositories;

import com.userfront.backend.domain.PrimaryTransaction;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by root on 02/07/17.
 */
@Repository
public interface PrimaryTransactionsRepository extends CrudRepository<PrimaryTransaction, Integer> {
	
	List<PrimaryTransaction> findAll();
}
