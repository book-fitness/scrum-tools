package net.scrumtools.dto;

import net.scrumtools.entity.Room;

import java.util.List;
import java.util.stream.Collectors;

public class RoomDto {
    private String roomId;
    private long createdDate;
    private long lastUpdatedDate;
    private List<UserDto> users;
    private RoomTimerDto totalTimer;
    private boolean roomOrder;
    private String roomName;

    public RoomDto(Room room) {
        this.roomId = room.getRoomId().toString();
        this.createdDate = room.getCreatedDate().getTime();
        this.lastUpdatedDate = room.getLastUpdatedDate().getTime();
        this.users = room.getAllUsers().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
        this.totalTimer = new RoomTimerDto(room.getTotalTimer());
        this.roomOrder = room.isRandomOrder();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(long lastUpdatedDate) {
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

    public boolean isRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(boolean roomOrder) {
        this.roomOrder = roomOrder;
    }
}
