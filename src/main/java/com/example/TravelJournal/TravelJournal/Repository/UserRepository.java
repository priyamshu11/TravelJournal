package com.example.TravelJournal.TravelJournal.Repository;

import com.example.TravelJournal.TravelJournal.Model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users,String> {
    Users findByUsername(String username);
    boolean existsByUsername(String username);
}
