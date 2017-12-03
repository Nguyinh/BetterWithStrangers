package ubiquasif.uqac.betterwithstrangers.Models;

import java.util.Date;

/**
 * Created by Amandine on 02/12/2017.
 */

public class Notification {

    private String userId;
    private String content;
    private Date timestamp;

    public Notification() {
    }

    public Notification(String userId, String content, Date timestamp) {
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
