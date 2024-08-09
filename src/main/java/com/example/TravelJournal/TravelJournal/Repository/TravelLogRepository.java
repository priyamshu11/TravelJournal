package com.example.TravelJournal.TravelJournal.Repository;

import com.example.TravelJournal.TravelJournal.Model.TravelLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TravelLogRepository extends MongoRepository<TravelLog,String> {
    List<TravelLog> findByTripId(String tripId);
}
