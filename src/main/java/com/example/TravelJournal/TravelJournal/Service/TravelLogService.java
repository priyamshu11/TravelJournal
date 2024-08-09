package com.example.TravelJournal.TravelJournal.Service;

import com.example.TravelJournal.TravelJournal.Model.TravelLog;
import com.example.TravelJournal.TravelJournal.Repository.TravelLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TravelLogService {
    @Autowired
    private TravelLogRepository travelLogRepository;

    // Create a new travel log
    public TravelLog createTravelLog(TravelLog travelLog) {
        return travelLogRepository.save(travelLog);
    }

    // Retrieve a travel log by ID
    public TravelLog findTravelLogById(String id) {
        return travelLogRepository.findById(id).orElse(null);
    }

    // Retrieve all travel logs
    public List<TravelLog> findAllTravelLogs() {
        return travelLogRepository.findAll();
    }

    // Retrieve all travel logs for a specific trip
    public List<TravelLog> findTravelLogsByTripId(String tripId) {
        return travelLogRepository.findByTripId(tripId);
    }

    // Update a travel log
    public TravelLog updateTravelLog(String id, TravelLog travelLogDetails) {
        Optional<TravelLog> optionalTravelLog = travelLogRepository.findById(id);
        if (optionalTravelLog.isPresent()) {
            TravelLog travelLog = optionalTravelLog.get();
            travelLog.setTitle(travelLogDetails.getTitle());
            travelLog.setDescription(travelLogDetails.getDescription());
            travelLog.setPhotoUrl(travelLogDetails.getPhotoUrl());
            travelLog.setVideoUrl(travelLogDetails.getVideoUrl());
            travelLog.setLocation(travelLogDetails.getLocation());
            travelLog.setDate(travelLogDetails.getDate());
            return travelLogRepository.save(travelLog);
        }
        return null;
    }

    // Delete a travel log by ID
    public boolean deleteTravelLogById(String id) {
        if (travelLogRepository.existsById(id)) {
            travelLogRepository.deleteById(id);
            return true;
        }
        return false;
    }
}