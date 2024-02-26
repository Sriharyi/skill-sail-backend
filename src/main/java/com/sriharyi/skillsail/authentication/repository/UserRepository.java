package com.sriharyi.skillsail.authentication.repository;

import com.sriharyi.skillsail.authentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface UserRepository extends MongoRepository<User,String> {
    public Optional<User>  findByEmail(String email);
    public boolean existsByEmail(String email);
}
