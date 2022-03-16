package net.scrumtools.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Room {
    private RoomId roomId;
    private Date createdDate = new Date();
    private Date lastUpdatedDate = new Date();
    private List<User> users = new ArrayList<>();
    private RoomTimer totalTimer = new RoomTimer();

    public Room(RoomId roomId) {
        this.roomId = roomId;
    }

    private void updateDate() {
        lastUpdatedDate = new Date();
    }

    public void addUser(User user) {
        users.add(user);
        updateDate();
    }

    public void removeUser(User user) {
        users.remove(user);
        updateDate();
    }

    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getSessionId().equals(userId)) return user;
        }
        return null;
    }

    public void removeUserById(String userId) {
        removeUser(getUserById(userId));
        updateDate();
    }

    public void startTimerOfUser(String userId) {
        getUserById(userId).startSpeaking();
        totalTimer.start();
        updateDate();
    }

    public void stopTimerOfUser(String userId) {
        getUserById(userId).stopSpeaking();
        totalTimer.stop();
        updateDate();
    }
}
