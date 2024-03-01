package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String>
{

}
