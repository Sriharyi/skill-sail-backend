package com.sriharyi.skillsail.authentication.repository;

import com.sriharyi.skillsail.authentication.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface TokenRepository extends MongoRepository<Token , String> {

    @Query("{ 'user.id' : ?0, 'expired' : false, 'revoked' : false }")
    List<Token> findActiveTokensByUserId(String userId);

    Optional<Token> findByToken(String token);

}
