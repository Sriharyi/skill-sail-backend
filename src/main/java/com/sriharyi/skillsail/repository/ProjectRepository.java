package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.Project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{
    List<Project> findAllByDeletedFalse();

    Page<Project> findAllByDeletedFalseAndStatus(Pageable pageable, String status);

    List<Project> findAllByEmployerProfileId_IdAndDeletedFalse(String employerId);

    List<Project> findAllBySelectedFreelancerId_IdAndDeletedFalse(String freelancerId);

    Page<Project> findAllByEmployerProfileId_IdAndDeletedFalse(Pageable pageable, String employerId);

    @Query("{ 'skills': { $regex: ?0, $options: 'i' } }")
    Page<Project> findBySkillsContaining(String skill, Pageable pageable);

    @Query("{ 'skills': { $regex: ?0, $options: 'i' }, 'employerProfileId.id': ?1 }")
    Page<Project> findBySkillsContainingAndEmployerProfileId_Id(String skill, String employerId, Pageable pageable);
}
