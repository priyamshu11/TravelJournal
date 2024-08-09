package com.example.TravelJournal.TravelJournal.Service;

import com.example.TravelJournal.TravelJournal.Model.Trips;
import com.example.TravelJournal.TravelJournal.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    // Create a new trip
    public Trips createTrip(Trips trip) {
        return tripRepository.save(trip);
    }

    // Retrieve a trip by ID
    public Trips findTripById(String id) {
        return tripRepository.findById(id).orElse(null);
    }

    // Retrieve all trips
    public List<Trips> findAllTrips() {
        return tripRepository.findAll();
    }

    // Retrieve all trips for a specific user
    public List<Trips> findTripsByUserId(String userId) {
        return tripRepository.findByUserId(userId);
    }

    // Update a trip
    public Trips updateTrip(String id, Trips tripDetails) {
        Trips trip = findTripById(id);
        if (trip != null) {
            trip.setTitle(tripDetails.getTitle());
            trip.setDescription(tripDetails.getDescription());
            trip.setTravelLogs(tripDetails.getTravelLogs());
            // Update other fields as needed
            return tripRepository.save(trip);
        }
        return null;
    }

    // Delete a trip by ID
    public boolean deleteTripById(String id) {
        if (tripRepository.existsById(id)) {
            tripRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
