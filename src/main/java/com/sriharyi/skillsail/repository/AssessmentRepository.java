package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.Assessment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssessmentRepository extends MongoRepository<Assessment, String>{

}
