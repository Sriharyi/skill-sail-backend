package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.Assessment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends MongoRepository<Assessment, String>{

    Optional<Assessment> findFirstByFreelancerProfileAndSkillOrderByUpdatedDateDesc(String freelancerProfileId, String skillId);

    List<Assessment> findByFreelancerProfile_Id(String freelancerProfileId);
}
