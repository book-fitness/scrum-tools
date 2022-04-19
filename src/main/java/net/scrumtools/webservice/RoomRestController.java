package net.scrumtools.webservice;

import net.scrumtools.dto.RoomDto;
import net.scrumtools.entity.Room;
import net.scrumtools.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomRestController {

    @Autowired
    private RoomService service;

    @GetMapping("/room-state/{roomId}")
    public ResponseEntity<RoomDto> getRoomState(@PathVariable("roomId") String roomId) {
        Room room = service.getRoomById(roomId);
        return new ResponseEntity<>(new RoomDto(room), HttpStatus.OK);
    }
}
