package net.scrumtools.websocket;

import net.scrumtools.dto.RoomDto;
import net.scrumtools.dto.UserActionChangeNameDto;
import net.scrumtools.dto.UserActionDto;
import net.scrumtools.dto.UserActionRemoveDto;
import net.scrumtools.entity.Room;
import net.scrumtools.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class UserActionWebSocket {
    @Autowired
    private RoomService service;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public UserActionWebSocket() {
        //System.out.println("UserActionWebSocket created");
    }

    private void sendResponseToRoomTopic(Room room) {
        String destination = "/topic/room/" + room.getRoomId();
        Object payload = new RoomDto(room);
        //System.out.println("Response: " + destination + ", " + room);
        messagingTemplate.convertAndSend(destination, payload);
    }

    @MessageMapping("/user-action/start")
    public void userActionStart(UserActionDto userActionDto) {
        //System.out.println("==== userActionStart(): " + userActionDto);
        Room room = service.getRoomById(userActionDto.getRoomId());
        room.startTimerByUserId(userActionDto.getUserId());
        sendResponseToRoomTopic(room);
    }

    @MessageMapping("/user-action/stop")
    public void userActionStop(UserActionDto userActionDto) {
        //System.out.println("==== userActionStop(): " + userActionDto);
        Room room = service.getRoomById(userActionDto.getRoomId());
        room.stopTimerByUserId(userActionDto.getUserId());
        sendResponseToRoomTopic(room);
    }

    @MessageMapping("/user-action/pause")
    public void userActionPause(UserActionDto userActionDto) {
        //System.out.println("==== userActionPause(): " + userActionDto);
        Room room = service.getRoomById(userActionDto.getRoomId());
        room.pauseTimerByUserId(userActionDto.getUserId());
        sendResponseToRoomTopic(room);
    }

    @MessageMapping("/user-action/change-name")
    public void userActionChangeName(UserActionChangeNameDto userActionChangeNameDto) {
        //System.out.println("==== userActionChangeName(): " + userActionChangeNameDto);
        Room room = service.getRoomById(userActionChangeNameDto.getRoomId());
        room.changeUserNameById(userActionChangeNameDto.getUserId(), userActionChangeNameDto.getNewUserName());
        sendResponseToRoomTopic(room);
    }

    @MessageMapping("/user-action/remove")
    public void userActionRemove(UserActionRemoveDto userActionRemoveDto) {
        //System.out.println("==== userActionRemove(): " + userActionRemoveDto);
        Room room = service.getRoomById(userActionRemoveDto.getRoomId());
        room.removeUser(room.getUserById(userActionRemoveDto.getRemovingUserId()));
        sendResponseToRoomTopic(room);
    }
}
