package ubiquasif.uqac.betterwithstrangers.Models;

import java.util.List;

public class User {
    private String userId;
    private String name;
    private List<String> preferences;
    private int guestRating;
    private int hostRating;

    public User() {}

    public User(String userId, String name) {
        this(userId, name, null, 0, 0);
    }

    public User(String userId, String name, List<String> preferences, int guestRating, int hostRating) {
        this.userId = userId;
        this.name = name;
        this.preferences = preferences;
        this.guestRating = guestRating;
        this.hostRating = hostRating;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<String> getPreferences() { return preferences; }

    public int guestRating() { return guestRating; }

    public int hostRating() { return hostRating; }


    /*public User(String userId, String name, List<Event> events) {
        this(userId, name, null, events, 0, 0);
    }

    public User(String userId, String name, List<String> preferences,
                List<Event> events, int guestRating, int hostRating) {
        this.userId = userId;
        this.name = name;
        this.preferences = preferences;
        this.events = events;
        this.guestRating = guestRating;
        this.hostRating = hostRating;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<String> getPreferences() { return preferences; }

    public List<Event> getEvents() { return events; }

    public int guestRating() { return guestRating; }

    public int hostRating() { return hostRating; }*/
}
