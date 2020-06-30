package com.hblog.chat.controller;

import static com.hblog.chat.model.UserResponse.GroupType.PRIVATE;
import static com.hblog.chat.model.UserResponse.GroupType.PUBLIC;
import static com.hblog.chat.model.UserResponse.MessageType.CHAT;
import static com.hblog.chat.model.UserResponse.MessageType.JOIN;
import static com.hblog.chat.utils.Constants.NEW_USER_JOINED;
import static java.lang.String.valueOf;
import static java.time.LocalDateTime.now;

import javax.servlet.http.HttpServletRequest;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hblog.chat.model.UserResponse;

@Controller
public class ChatController {

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String chat(HttpServletRequest request) {
        return "/dev/chat/chat";
    }

	@MessageMapping("/connect")
	@SendTo("/topic/connect")
	public UserResponse connect(@RequestParam String username, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", username);
		UserResponse userResponse = new UserResponse(username, NEW_USER_JOINED, JOIN, PUBLIC);
		return userResponse;
	}

	@MessageMapping("/message")
	@SendTo("/topic/message")
	public UserResponse getMessage(@Payload UserResponse userResponse) {

		userResponse.setMtype(CHAT);
		userResponse.setGtype(PUBLIC);
		userResponse.setTime(valueOf(now()));

		return userResponse;
	}

	@MessageMapping("{groupid}/connect")
	@SendTo("/topic/{groupid}/connect")
	public UserResponse connect(@DestinationVariable String groupid, @RequestParam String username,
			SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", username);
		UserResponse userResponse = new UserResponse(username, NEW_USER_JOINED, JOIN, PRIVATE);
		return userResponse;
	}

	@MessageMapping("{groupid}/message")
	@SendTo("/topic/{groupid}/message")
	public UserResponse getMessage(@DestinationVariable String groupid, @Payload UserResponse userResponse) {

		userResponse.setMtype(CHAT);
		userResponse.setGtype(PRIVATE);
		userResponse.setTime(valueOf(now()));

		return userResponse;
	}

}
