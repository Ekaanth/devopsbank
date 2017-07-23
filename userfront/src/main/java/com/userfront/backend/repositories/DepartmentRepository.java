package com.userfront.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.userfront.backend.domain.Department;

/**
 * Created by root on 01/07/17.
 */
@Repository
@Transactional
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    Department findByName(String name);
}
