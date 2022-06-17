package net.scrumtools.entity;

import java.util.*;

public class Room {
    private RoomId roomId;
    private Date createdDate = new Date();
    private Date lastUpdatedDate = new Date();
    private List<User> users = new ArrayList<>();
    private RoomTimer totalTimer = new RoomTimer();

    public Room(RoomId roomId) {
        this.roomId = roomId;
    }

    public RoomId getRoomId() {
        return this.roomId;
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
            if (user.getUserId().toString().equals(userId)) return user;
        }
        return null;
    }

    public User getUserBySessionId(String sessionId) {
        for (User user : users) {
            if (user.getSessionId().equals(sessionId)) return user;
        }
        return null;
    }

    public void removeUserBySessionId(String sessionId) {
        removeUser(getUserBySessionId(sessionId));
        updateDate();
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void startTimerBySessionId(String sessionId) {
        getUserBySessionId(sessionId).startSpeaking();
        totalTimer.start();
        updateDate();
    }

    public void stopTimerBySessionId(String sessionId) {
        getUserBySessionId(sessionId).stopSpeaking();
        totalTimer.stop();
        updateDate();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public RoomTimer getTotalTimer() {
        return totalTimer;
    }

    public void nextUserAfterBySessionId(String sessionId) {
        stopTimerBySessionId(sessionId);
        User nextUser = findUserAfterBySessionId(sessionId);
        if (nextUser == null) return;
        startTimerBySessionId(nextUser.getSessionId());
    }

    private User findUserAfterBySessionId(String sessionId) {
        boolean flag = false;
        User result = null;
        for (User user : users) {
            if (flag) {
                result = user;
                break;
            }
            if (user.getSessionId().equals(sessionId)) {
                flag = true;
            }
        }
        return result;
    }

    public boolean isExistUserBySessionId(String sessionId) {
        for (User user : getAllUsers()) {
            if (Objects.equals(user.getSessionId(), sessionId)) return true;
        }
        return false;
    }

    public boolean hasUserWithSessionId(String sessionId) {
        for (User user : users) {
            if (Objects.equals(user.getSessionId(), sessionId)) return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }
}
