package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.Project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{
    List<Project> findAllByDeletedFalse();

    Page<Project> findAllByDeletedFalseAndStatus(Pageable pageable, String status);
}
