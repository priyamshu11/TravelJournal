package com.example.TravelJournal.TravelJournal.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Document(collection = "travelLogs")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TravelLog {
    @Id
    private String id;
    private String tripId;
    private String title;
    private String description;
    private String photoUrl;
    private String videoUrl;
    private String location;
    private Date date;
}