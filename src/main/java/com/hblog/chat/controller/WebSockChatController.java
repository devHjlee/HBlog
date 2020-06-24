package com.hblog.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hblog.chat.dto.ChatRoomForm;
import com.hblog.chat.dto.WebSockChatRoom;
import com.hblog.chat.service.WebSockChatService;

@Controller
public class WebSockChatController {

    @Autowired
    WebSockChatService chatService;

    @GetMapping("/")
    public String rooms(Model model){
        model.addAttribute("rooms",chatService.findAllRoom());
        return "/dev/chat/roomList";
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable String id, Model model){
        WebSockChatRoom room = chatService.findRoomById(id);
        model.addAttribute("room",room);
        return "/dev/chat/chat";
    }

    @GetMapping("/new")
    public String make(Model model){
        ChatRoomForm form = new ChatRoomForm();
        model.addAttribute("form",form);
        return "/dev/chat/newRoom";
    }

    @PostMapping("/room/new")
    public String makeRoom(ChatRoomForm form){
        chatService.createChatRoom(form.getName());

        return "redirect:/";
    }
}
