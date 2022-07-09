package net.scrumtools.entity;

public class User {
    private String sessionId;
    private UserId userId;
    private String name;
    private RoomTimer userTime = new RoomTimer();
    private boolean anonymous = true;

    public User(String sessionId, UserId userId,  String name, long timerLimit) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.name = name;
        userTime.setTimerLimit(timerLimit);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomTimer getUserTime() {
        return userTime;
    }

    public void setUserTime(RoomTimer userTime) {
        this.userTime = userTime;
    }

    public boolean isSpeaking() {
        return userTime.isRunning();
    }

    public void startSpeaking() {
        userTime.start();
    }

    public void stopSpeaking() {
        userTime.stop();
    }

    public long getSpeakTime() {
        return userTime.getTotalTime();
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}
