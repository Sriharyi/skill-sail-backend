package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.FreeLancerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeLancerProfileRepository extends MongoRepository<FreeLancerProfile, String>{

    List<FreeLancerProfile> findAllByDeletedFalse();
}

