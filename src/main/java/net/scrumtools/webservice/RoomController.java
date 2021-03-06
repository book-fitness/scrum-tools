package net.scrumtools.webservice;

import net.scrumtools.entity.User;
import net.scrumtools.entity.UserId;
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

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/room/{roomId}")
    public ModelAndView showRoom(@PathVariable("roomId") String roomId, ModelAndView modelAndView) {
        Room room = roomService.getRoomById(roomId);

        if (!room.isExistUserBySessionId(httpSession.getId())) {
            room.addUser(new User(httpSession.getId(), UserId.createRandom(), "Anonymous", room.getTotalTimer().getTimerLimit()));
        }

        String userId = room.getUserBySessionId(httpSession.getId()).getUserId().toString();
        modelAndView.addObject("roomId", roomId);
        modelAndView.addObject("userId", userId);
        modelAndView.setViewName("room.html");
        return modelAndView;
    }

    @PostMapping("/room")
    public String createNewRoom(@RequestParam("userName") String userName,
                                @RequestParam("timerLimit") String timerLimitStr,
                                @RequestParam(value = "randomOrderCheckbox", required = false, defaultValue = "false") Boolean randomOrderCheckbox,
                                @RequestParam("roomName") String roomName) {

        Room room = roomService.createRoom(httpSession.getId(), userName, parseTimerLimit(timerLimitStr), randomOrderCheckbox, roomName);

        return "redirect:/room/" + room.getRoomId();
    }

    private static long parseTimerLimit(String timerLimitStr) {
        if (!timerLimitStr.matches("^[0-9]{1,2}:[0-9]{2}$"))
            throw new IllegalArgumentException("Timer limit must be like: NN:NN. Actual is " + timerLimitStr);

        int colonIndex = timerLimitStr.indexOf(":");
        int minutes = Integer.parseInt(timerLimitStr.substring(0, colonIndex));
        int seconds = Integer.parseInt(timerLimitStr.substring(colonIndex + 1));

        return minutes*60*1000L + seconds*1000L;
    }
}
