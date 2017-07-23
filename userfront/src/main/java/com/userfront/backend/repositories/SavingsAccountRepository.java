package com.userfront.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.userfront.backend.domain.SavingsAccount;

/**
 * Created by root on 01/07/17.
 */
@Repository
@Transactional
public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Integer> {

    SavingsAccount findByAccountNumber(int accountNumber);
}
