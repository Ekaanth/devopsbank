package com.userfront.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userfront.backend.domain.Department;
import com.userfront.backend.repositories.DepartmentRepository;

/**
 * Created by root on 01/07/17.
 */
@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department){

        return departmentRepository.save(department);
    }
}
