package net.scrumtools.webservice;

import net.scrumtools.dto.RoomDto;
import net.scrumtools.entity.Room;
import net.scrumtools.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class RoomRestController {

    @Autowired
    private HttpSession session;

    @Autowired
    private RoomService service;

    public RoomRestController() {
        System.out.println("RoomRestController created");
    }

    @GetMapping("/room-state/{roomId}")
    public ResponseEntity<RoomDto> getRoomState(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        return new ResponseEntity<>(new RoomDto(room), HttpStatus.OK);
    }

    @PostMapping("/room-state/{roomId}/poll")
    public ResponseEntity<Object> pollRoomState(@PathVariable("roomId") String roomId,
                                                @RequestParam("lastUpdatedDate") long lastUpdatedDate) {
        Room room = service.getRoomById(roomId);
        if (room != null && room.getLastUpdatedDate().getTime() > lastUpdatedDate) {
            return new ResponseEntity<>(new RoomDto(room), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }
    }

    @PostMapping("/room-start-action")
    public RoomDto startAction1(@RequestParam("roomId") String roomId,
                                @RequestParam("actionName") String actionName) {
        System.out.println("received");
        System.out.println("Action Name " + actionName);
        System.out.println("roomId " + roomId);
        System.out.println("ID " + session.getId());
        System.out.println("session " + session.hashCode() + " " + session.getClass().getName());
        Room room = service.getRoomById(roomId);
        room.startTimerOfUser(session.getId());
        return new RoomDto(room);
    }

    @PostMapping("/room/{roomId}/start-action")
    public ResponseEntity<RoomDto> startAction(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        room.startTimerOfUser(session.getId());
        return new ResponseEntity<>(new RoomDto(room), HttpStatus.OK);
    }

    @PostMapping("/room/{roomId}/stop-action")
    public RoomDto stopAction(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        room.stopTimerOfUser(session.getId());
        return new RoomDto(room);
    }

    @PostMapping("/room/{roomId}/pause-action")
    public RoomDto pauseAction(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        room.stopTimerOfUser(session.getId());
        return new RoomDto(room);
    }

    @PostMapping("/room/{roomId}/next-action")
    public RoomDto nextAction(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        room.nextUserAfter(session.getId());
        return new RoomDto(room);
    }
}
