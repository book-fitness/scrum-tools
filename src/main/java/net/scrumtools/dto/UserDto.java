package net.scrumtools.dto;

import net.scrumtools.entity.User;

public class UserDto {
    private String userId;
    private String name;
    private RoomTimerDto timer;
    private boolean anonymous;
    private boolean pause;
    private boolean active;
    private boolean running;
    private boolean isHost;

    public UserDto(User user) {
        this.userId = user.getUserId().toString();
        this.name = user.getName();
        this.timer = new RoomTimerDto(user.getUserTime());
        this.anonymous = user.isAnonymous();
        this.pause = user.isPause();
        this.active = user.isActive();
        this.running = user.isRunning();
        this.isHost = user.isHost();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomTimerDto getTimer() {
        return timer;
    }

    public void setTimer(RoomTimerDto timer) {
        this.timer = timer;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}
