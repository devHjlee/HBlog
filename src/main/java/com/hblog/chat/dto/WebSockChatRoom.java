package com.hblog.chat.dto;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hblog.chat.service.WebSockChatService;

public class WebSockChatRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    public static WebSockChatRoom create(String name){
        WebSockChatRoom chatRoom = new WebSockChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }

    public void handleActions(WebSocketSession session, WebSockChatMessage chatMessage, WebSockChatService chatService) {
        if (chatMessage.getType().equals(WebSockChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getWriter() + "님이 입장했습니다.");
        }else if (chatMessage.getType().equals(WebSockChatMessage.MessageType.LEAVE)) {
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getWriter() + "님이 퇴장했습니다.");
        }else {
            chatMessage.setMessage(chatMessage.getWriter() + ":" + chatMessage.getMessage());
        }
        sendMessage(chatMessage, chatService);
        try {
            send(chatMessage, chatService);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void send(WebSockChatMessage chatMessage, WebSockChatService chatService) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.
                                    writeValueAsString(chatMessage.getMessage()));
        for(WebSocketSession sess : sessions){
            sess.sendMessage(textMessage);
        }
    }
    public <T> void sendMessage(T message, WebSockChatService chatService) {

        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WebSocketSession> getSessions() {
        return sessions;
    }

    public void setSessions(Set<WebSocketSession> sessions) {
        this.sessions = sessions;
    }

}
