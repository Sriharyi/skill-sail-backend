package com.sriharyi.skillsail.authentication.repository;

import com.sriharyi.skillsail.authentication.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByNameIgnoreCase(String name);
}
