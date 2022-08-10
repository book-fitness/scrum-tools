package net.scrumtools.entity;

import java.util.*;
import java.util.stream.Collectors;

public class Room {
    private RoomId roomId;
    private Date createdDate = new Date();
    private Date lastUpdatedDate = new Date();
    private List<User> users = new ArrayList<>();
    private RoomTimer totalTimer = new RoomTimer();
    private boolean randomOrder;
    private String name;

    public Room(RoomId roomId) {
        this.roomId = roomId;
    }

    public Room(RoomId roomId, boolean randomOrder) {
        this.roomId = roomId;
        this.randomOrder = randomOrder;
    }

    public RoomId getRoomId() {
        return this.roomId;
    }

    private void updateDate() {
        lastUpdatedDate = new Date();
    }

    public boolean isRandomOrder() {
        return randomOrder;
    }

    public void setRandomOrder(boolean randomOrder) {
        this.randomOrder = randomOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        if (!users.stream().anyMatch(User::isActive)) {
            user.setActive(true);
        }
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

    public void changeUserName(String sessionId, String name) {
        if (!isExistUserBySessionId(sessionId)) return;
        changeUserNameOf(getUserBySessionId(sessionId), name);
    }

    public void changeUserNameById(String userId, String name) {
        changeUserNameOf(getUserById(userId), name);
    }

    public void changeUserNameOf(User user, String name) {
        user.setAnonymous(false);
        if (name != null && !name.equals("null")) {
            user.setName(name);
        } else {
            user.setName("Anonymous");
        }
        updateDate();
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
        startTimerOf(getUserBySessionId(sessionId));
    }

    public void startTimerByUserId(String userId) {
        startTimerOf(getUserById(userId));
    }

    public void startTimerOf(User user) {
        if (user.isSpeaking()) return;

        stopAll();
        start(user);
    }

    public void stopTimerBySessionId(String sessionId) {
        stopTimerOf(findSpeakingUser());
    }

    public void stopTimerByUserId(String userId) {
        User user = getUserById(userId);
        stopTimerOf(user);
        //stopTimerOf(findSpeakingUser());
    }

    public void stopTimerOf(User user) {
        stop(user);
        User nextUser = findNextUser(user);
        if (nextUser != null) nextUser.setActive(true);
    }

    private User findSpeakingUser() {
        for (User user : users) {
            if (user.isSpeaking()) return user;
        }

        return null;
    }

    private User findNextUser(User user) {
        if (randomOrder) {
            return findRandomUser();
        } else {
            int indexOfNextUser = users.indexOf(user) + 1;
            return indexOfNextUser <= users.size() - 1
                 ? users.get(indexOfNextUser)
                 : null;
        }
    }

    private User findRandomUser() {
        Random random = new Random();
        List<User> users = getNotSpeakingUsers();
        if (users.isEmpty()) return null;
        int randomIndex = random.nextInt(users.size());
        return users.get(randomIndex);
    }

    private List<User> getNotSpeakingUsers() {
        return users.stream()
                .filter(u -> u.getUserTime().getTotalTime() == 0)
                .collect(Collectors.toList());
    }

    public void pauseTimerBySessionId(String sessionId) {
        pause(getUserBySessionId(sessionId));
    }

    public void pauseTimerByUserId(String userId) {
        pause(getUserById(userId));
    }

    private void start(User user) {
        user.startSpeaking();
        totalTimer.start();
        updateDate();
    }

    private void stop(User user) {
        user.stopSpeaking();
        totalTimer.stop();
        updateDate();
    }

    private void pause(User user) {
        if (user.isPause()) {
            totalTimer.start();
        } else {
            totalTimer.stop();
        }
        user.pauseSpeaking();
        updateDate();
    }

    private void stopAll() {
        for (User user : users) {
            if (user.isSpeaking()) user.stopSpeaking();
        }
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

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", createdDate=" + createdDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                ", users=" + users +
                ", totalTimer=" + totalTimer +
                ", randomOrder=" + randomOrder +
                ", name='" + name + '\'' +
                '}';
    }
}
