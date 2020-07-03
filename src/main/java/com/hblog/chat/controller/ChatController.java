package com.hblog.chat.controller;

import static com.hblog.chat.model.UserResponse.GroupType.PRIVATE;
import static com.hblog.chat.model.UserResponse.GroupType.PUBLIC;
import static com.hblog.chat.model.UserResponse.MessageType.CHAT;
import static com.hblog.chat.model.UserResponse.MessageType.JOIN;
import static com.hblog.chat.utils.Constants.NEW_USER_JOINED;
import static java.lang.String.valueOf;
import static java.time.LocalDateTime.now;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hblog.chat.model.UserResponse;
import com.hblog.chat.service.ChatService;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;

    @RequestMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", chatService.getChatRooms());

        return "/dev/chat/chatList";
    }

    @RequestMapping(value = "/chat/{chatRoomId}/{userName}", method = RequestMethod.GET)
    public String chat(HttpServletRequest request,@PathVariable("chatRoomId")String roomId,@PathVariable("userName")String userName,Model model) {
        
        model.addAttribute("roomId",roomId);
        model.addAttribute("userName",userName);
        
        return "/dev/chat/chat";
    }
    @RequestMapping(value = "/newChat/{roomName}", method = RequestMethod.GET)
    public String newChat(HttpServletRequest request,@PathVariable("roomName")String roomName,Model model) {
        String groupid = UUID.randomUUID().toString();
        chatService.createChatRoom(roomName, groupid);
        model.addAttribute("rooms", chatService.getChatRooms());
        return "/dev/chat/chatList";
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
	public UserResponse connect(@DestinationVariable String groupid, @RequestParam String username,@RequestParam String roomname,
			SimpMessageHeaderAccessor headerAccessor) {

	    headerAccessor.getSessionAttributes().put("username", username);
	    String sessionId = headerAccessor.getSessionId();
	    headerAccessor.getSessionAttributes().put("sessionId", sessionId);

	    UserResponse userResponse = new UserResponse(username, NEW_USER_JOINED, JOIN, PRIVATE, sessionId);

		return userResponse;
	}

	@MessageMapping("{groupid}/message")
	@SendTo("/topic/{groupid}/message")
	public UserResponse getMessage(@DestinationVariable String groupid, @Payload UserResponse userResponse, SimpMessageHeaderAccessor headerAccessor) {

        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        System.out.println(sessionId);
        userResponse.setMtype(CHAT);
		userResponse.setGtype(PRIVATE);
		userResponse.setSessionId(sessionId);
		userResponse.setTime(valueOf(now()));

		return userResponse;
	}

}
