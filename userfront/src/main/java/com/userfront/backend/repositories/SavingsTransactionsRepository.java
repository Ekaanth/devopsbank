package com.userfront.backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.userfront.backend.domain.SavingsTransaction;

/**
 * Created by root on 02/07/17.
 */
@Repository
public interface SavingsTransactionsRepository extends CrudRepository<SavingsTransaction, Integer> {
	
	List<SavingsTransaction> findAll();
}
