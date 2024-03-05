package com.sriharyi.skillsail.repository;

import com.sriharyi.skillsail.model.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BidRepository extends MongoRepository<Bid,String> {

}
