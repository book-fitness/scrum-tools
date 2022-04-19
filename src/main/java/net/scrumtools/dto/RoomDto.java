package net.scrumtools.dto;

import net.scrumtools.entity.Room;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RoomDto {
    private String roomId;
    private Date createdDate;
    private Date lastUpdatedDate;
    private List<UserDto> users;
    private RoomTimerDto totalTimer;

    public RoomDto(Room room) {
        this.roomId = room.getRoomId().toString();
        this.createdDate = room.getCreatedDate();
        this.lastUpdatedDate = room.getLastUpdatedDate();
        this.users = room.getAllUsers().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
        this.totalTimer = new RoomTimerDto(room.getTotalTimer());
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }

    public RoomTimerDto getTotalTimer() {
        return totalTimer;
    }

    public void setTotalTimer(RoomTimerDto totalTimer) {
        this.totalTimer = totalTimer;
    }
}
