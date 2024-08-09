package com.example.TravelJournal.TravelJournal.Repository;

import com.example.TravelJournal.TravelJournal.Model.Trips;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TripRepository extends MongoRepository<Trips,String> {
    List<Trips> findByUserId(String userId);
}
