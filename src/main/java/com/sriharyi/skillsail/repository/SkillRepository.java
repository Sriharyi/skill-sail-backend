package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends MongoRepository<Skill,String> {

    List<Skill> findAllByDeletedIsFalse();
}
