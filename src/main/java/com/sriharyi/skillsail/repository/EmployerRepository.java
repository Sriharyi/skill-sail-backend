package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.EmployerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployerRepository extends MongoRepository<EmployerProfile,String> {
  List<EmployerProfile> findAllByDeletedFalse();
}
