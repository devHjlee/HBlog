package com.hblog.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hblog.chat.dto.WebSockChatRoom;

@Service
public class WebSockChatService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, WebSockChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<WebSockChatRoom> findAllRoom() {
        List chatRooms = new ArrayList<>(chatRooms.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public WebSockChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public WebSockChatRoom createChatRoom(String name){
        WebSockChatRoom chatRoom = WebSockChatRoom.create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
