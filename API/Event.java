package API;

import java.time.LocalDateTime;

public class Event {
    private final int eventId;
    private final int venueId;
    private final String name;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int length;  // Length in minutes

    // Constructor
    public Event(int eventId, int venueId, String name, String description,
                 LocalDateTime startTime, LocalDateTime endTime, int length) {
        this.eventId = eventId;
        this.venueId = venueId;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.length = length;
    }

    // Getters
    public int getEventId() {return eventId;}
    public int getVenueId() {return venueId;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public LocalDateTime getStartTime() {return startTime;}
    public LocalDateTime getEndTime() {return endTime;}
    public int getLength() {return length;}
}

