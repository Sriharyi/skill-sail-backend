package com.sriharyi.skillsail.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sriharyi.skillsail.model.Bid;

public interface BidRepository extends MongoRepository<Bid,String> {

    List<Bid> findAllByProjectId_Id(String projectId);

    List<Bid> findAllByFreelancerId_Id(String freelancerId);
}   
