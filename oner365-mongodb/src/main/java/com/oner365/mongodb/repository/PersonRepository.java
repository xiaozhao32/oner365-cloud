package com.oner365.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.oner365.mongodb.entity.Person;

/**
 * Person Repository
 * 
 * @author zhaoyong
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

}
