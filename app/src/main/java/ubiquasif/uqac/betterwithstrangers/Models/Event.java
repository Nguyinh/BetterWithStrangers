package ubiquasif.uqac.betterwithstrangers.Models;

import java.util.Date;
import java.util.List;

public class Event {
    private String userId;
    private String name;
    private boolean isPrivate;
    private List<String> tags;
    private Date timestamp;
    private String placeName;

    public Event() {}

    public Event(String userId, String name, boolean isPrivate, List<String> tags, Date timestamp, String placeName) {
        this.userId = userId;
        this.name = name;
        this.isPrivate = isPrivate;
        this.tags = tags;
        this.timestamp = timestamp;
        this.placeName = placeName;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public List<String> getTags() {
        return tags;
    }

    public Date getTimestamp() { return timestamp; }

    public String getPlaceName() {
        return placeName;
    }
}
