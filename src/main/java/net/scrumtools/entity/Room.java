package net.scrumtools.entity;

import java.util.*;

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

    public void pauseUserByUserId(String userId) {
        pauseUser(findSpeakingUser());
    }

    public void stopTimerByUserId(String userId) {
        stopTimerOf(findSpeakingUser());
    }

    public void stopTimerOf(User user) {
        stop(user);
        startSpeakingNextUser(user);
    }

    private User findSpeakingUser() {
        for (User user : users) {
            if (user.isSpeaking()) return user;
        }

        return null;
    }

    private void startSpeakingNextUser(User user) {
        if (randomOrder) {
            startSpeakingOfRandomUser();
        } else if (isNotSpeakingUserExist()) {
            int indexOfNextUser = users.indexOf(user) + 1;
            if (indexOfNextUser <= users.size() - 1) startTimerBySessionId(users.get(indexOfNextUser).getSessionId());
        }
    }

    private void startSpeakingOfRandomUser() {
        Random random = new Random();
        int randomIndex = random.nextInt(users.size());
        if (isNotSpeakingUserExist()) {
            while(true) {
                User user = users.get(randomIndex);
                if (user.getUserTime().getTotalTime() == 0) {
                    startTimerBySessionId(user.getSessionId());
                    break;
                }
            }
        }
    }

    private boolean isNotSpeakingUserExist() {
        return users.stream().anyMatch(u -> u.getUserTime().getTotalTime() == 0);
    }

    public void pauseTimerBySessionId(String sessionId) {
        pauseTimerOf(getUserBySessionId(sessionId));
    }

    public void pauseTimerByUserId(String userId) {
        pauseTimerOf(getUserById(userId));
    }

    public void pauseTimerOf(User user) {
        if (user.isSpeaking()) {
            stop(user);
        } else {
            stopAll();
            start(user);
        }
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

    public void pauseUser(User user) {
        user.pauseAction();
        totalTimer.stop();
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
}
