package com.dailytool.webservice;

import com.dailytool.service.RoomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomController {

    @Autowired
    private RoomNumberGenerator roomNumberGenerator;

    @GetMapping("/room/{roomId}")
    public ModelAndView showRoom(@PathVariable("roomId") String roomId, ModelAndView modelAndView) {
        modelAndView.addObject("roomId", roomId);
        modelAndView.setViewName("room.html");
        return modelAndView;
    }

    @PostMapping("/room")
    public String createNewRoom(ModelAndView modelAndView) {
        String roomId = roomNumberGenerator.generateRoomNumber();
        return "redirect:/room/" + roomId;
    }
}
