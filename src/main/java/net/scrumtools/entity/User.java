package net.scrumtools.entity;

public class User {
    private String sessionId;
    private UserId userId;
    private String name;
    private RoomTimer userTime = new RoomTimer();
    private boolean anonymous = true;
    private boolean running = false;
    private boolean pause = false;
    private boolean active = false;
    private boolean isHost = false;

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
        running = true;
        userTime.start();
    }

    public void pauseSpeaking() {
        if (pause) {
            userTime.start();
        } else {
            userTime.stop();
        }
        pause = !pause;
    }

    public void stopSpeaking() {
        active = false;
        running = false;
        pause = false;
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    @Override
    public String toString() {
        return "User{" +
                "sessionId='" + sessionId + '\'' +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", userTime=" + userTime +
                ", anonymous=" + anonymous +
                ", pause=" + pause +
                ", active=" + active +
                ", isHost=" + isHost +
                '}';
    }
}
