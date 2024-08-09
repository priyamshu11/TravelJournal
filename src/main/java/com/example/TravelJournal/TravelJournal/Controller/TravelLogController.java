package com.example.TravelJournal.TravelJournal.Controller;

import com.example.TravelJournal.TravelJournal.Model.TravelLog;
import com.example.TravelJournal.TravelJournal.Service.TravelLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelLogs")
public class TravelLogController {
    @Autowired
    private TravelLogService travelLogService;

    // Create a new travel log
    @PostMapping
    public TravelLog createTravelLog(@RequestBody TravelLog travelLog) {
        return travelLogService.createTravelLog(travelLog);
    }

    // Get a travel log by ID
    @GetMapping("/{id}")
    public TravelLog getTravelLogById(@PathVariable String id) {
        return travelLogService.findTravelLogById(id);
    }

    // Get all travel logs
    @GetMapping
    public List<TravelLog> getAllTravelLogs() {
        return travelLogService.findAllTravelLogs();
    }

    // Get all travel logs for a specific trip
    @GetMapping("/trip/{tripId}")
    public List<TravelLog> getTravelLogsByTripId(@PathVariable String tripId) {
        return travelLogService.findTravelLogsByTripId(tripId);
    }

    // Update a travel log
    @PutMapping("/{id}")
    public TravelLog updateTravelLog(@PathVariable String id, @RequestBody TravelLog travelLogDetails) {
        return travelLogService.updateTravelLog(id, travelLogDetails);
    }

    // Delete a travel log by ID
    @DeleteMapping("/{id}")
    public boolean deleteTravelLogById(@PathVariable String id) {
        return travelLogService.deleteTravelLogById(id);
    }
}
