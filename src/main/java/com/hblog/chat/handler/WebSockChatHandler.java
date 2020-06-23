package com.hblog.chat.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hblog.chat.dto.WebSockChatRoom;
import com.hblog.chat.model.WebSockChatModel;
import com.hblog.chat.service.WebSockChatService;

@Component
public class WebSockChatHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSockChatHandler.class);

    @Autowired
    WebSockChatService chatService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("payload {}", payload);
        //TextMessage textMessage = new TextMessage("Welcome chatting sever~^^");
        //session.sendMessage(textMessage);
        WebSockChatModel chatMessage = objectMapper.readValue(payload, WebSockChatModel.class);
        WebSockChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓 연결
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //소켓 종료
    }

}
