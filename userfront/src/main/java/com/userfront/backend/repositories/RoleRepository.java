package com.userfront.backend.repositories;

import com.userfront.backend.domain.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by root on 01/07/17.
 */
@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String roleName);
}
