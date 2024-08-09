package com.example.TravelJournal.TravelJournal.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection="Trips")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Trips {
    @Id
    private String id;
    private String userId;
    private String title;
    private String description;
    private List<TravelLog> travelLogs;
}
