package com.ichwan.springmongodb.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PersonRepository extends MongoRepository<Person, String> {

    @Query("{name: '?0'}")
    Person findPersonByName(String name);

    Person findByAddress(String address);
}
