package net.scrumtools.webservice;

import net.scrumtools.dto.RoomDto;
import net.scrumtools.entity.Room;
import net.scrumtools.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@RestController
public class RoomRestController {

    @Autowired
    private ServletContext context;

    @Autowired
    private HttpSession session;

    @Autowired
    private RoomService service;

    public RoomRestController() {
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

    @PostMapping("/room/{roomId}/start-action")
    public ResponseEntity<RoomDto> startAction(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        room.startTimerBySessionId(session.getId());
        return new ResponseEntity<>(new RoomDto(room), HttpStatus.OK);
    }

    @PostMapping("/room/{roomId}/stop-action")
    public ResponseEntity<RoomDto> stopAction(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        room.stopTimerBySessionId(session.getId());
        return new ResponseEntity<>(new RoomDto(room), HttpStatus.OK);
    }

    @PostMapping("/room/{roomId}/pause-action")
    public ResponseEntity<RoomDto> pauseAction(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        room.pauseTimerBySessionId(session.getId());
        return new ResponseEntity<>(new RoomDto(room), HttpStatus.OK);
    }
}
