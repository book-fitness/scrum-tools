package net.scrumtools.dto;

import net.scrumtools.entity.User;

public class UserDto {
    private String name;
    private RoomTimerDto timer;

    public UserDto(User user) {
        this.name = user.getName();
        this.timer = new RoomTimerDto(user.getUserTime());
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
}
