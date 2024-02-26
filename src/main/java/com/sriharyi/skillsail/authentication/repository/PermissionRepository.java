package com.sriharyi.skillsail.authentication.repository;

import com.sriharyi.skillsail.authentication.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends MongoRepository<Permission, String>{

    Optional<Permission> findByNameIgnoreCase(String permission);
    Set<Permission> findAllByNameIn(List<String> strings);




}
