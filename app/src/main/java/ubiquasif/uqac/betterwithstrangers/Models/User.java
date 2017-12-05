package ubiquasif.uqac.betterwithstrangers.Models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String name;
    private String minibio;
    private List<String> preferences;
    private int guestRating;
    private int hostRating;

    public User() {}

    public User(String userId, String name) {
        this(userId, name, "", new ArrayList<String>(), 0, 0);
    }

    public User(String userId, String name, String minibio, List<String> preferences, int guestRating, int hostRating) {
        this.userId = userId;
        this.name = name;
        this.minibio = minibio;
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

    public String getMinibio() {
        return minibio;
    }

    public List<String> getPreferences() { return preferences; }

    public int getGuestRating() { return guestRating; }

    public int getHostRating() { return hostRating; }

    public void addPreference(String newPreference) {
        if(preferences == null) {
            this.preferences = new ArrayList<String>();
        }
        this.preferences.add(newPreference);
    }
}
