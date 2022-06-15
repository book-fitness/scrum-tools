package net.scrumtools.web;

import net.scrumtools.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class UserSessionListener implements HttpSessionListener {

    @Autowired
    private RoomService roomService;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        roomService.userSessionExpired(event.getSession().getId());
    }
}
