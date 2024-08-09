package com.example.TravelJournal.TravelJournal.Controller;

import com.example.TravelJournal.TravelJournal.Model.Trips;
import com.example.TravelJournal.TravelJournal.Service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    @Autowired
    private TripService tripService;

    // Create a new trip
    @PostMapping
    public Trips createTrip(@RequestBody Trips trip) {
        return tripService.createTrip(trip);
    }

    // Get a trip by ID
    @GetMapping("/{id}")
    public Trips getTripById(@PathVariable String id) {
        return tripService.findTripById(id);
    }

    // Get all trips
    @GetMapping
    public List<Trips> getAllTrips() {
        return tripService.findAllTrips();
    }

    // Get all trips for a specific user
    @GetMapping("/user/{userId}")
    public List<Trips> getTripsByUserId(@PathVariable String userId) {
        return tripService.findTripsByUserId(userId);
    }

    // Update a trip
    @PutMapping("/{id}")
    public Trips updateTrip(@PathVariable String id, @RequestBody Trips tripDetails) {
        return tripService.updateTrip(id, tripDetails);
    }

    // Delete a trip by ID
    @DeleteMapping("/{id}")
    public boolean deleteTripById(@PathVariable String id) {
        return tripService.deleteTripById(id);
    }
}
