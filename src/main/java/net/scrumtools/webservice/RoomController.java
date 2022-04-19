package net.scrumtools.webservice;

import net.scrumtools.entity.User;
import net.scrumtools.service.RoomNumberGenerator;
import net.scrumtools.entity.Room;
import net.scrumtools.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private RoomNumberGenerator roomNumberGenerator;

    @GetMapping("/room/{roomId}")
    public ModelAndView showRoom(@PathVariable("roomId") String roomId, ModelAndView modelAndView) {;
        modelAndView.addObject("roomId", roomId);
        modelAndView.setViewName("room.html");
        return modelAndView;
    }

    @PostMapping("/room")
    public String createNewRoom(@RequestParam("userName") String userName) {
        Room room = roomService.createRoom(httpSession.getId(), userName);
        return "redirect:/room/" + room.getRoomId();
    }
}
