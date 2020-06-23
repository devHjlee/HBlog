package com.hblog.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hblog.chat.dto.WebSockChatRoom;
import com.hblog.chat.service.WebSockChatService;

@Controller
public class WebSockChatController {

    @Autowired
    WebSockChatService chatService;

    @PostMapping("/chat")
    public @ResponseBody WebSockChatRoom createRoom(@RequestParam String name) {
        return chatService.createChatRoom(name);
    }

    @GetMapping("/chat")
    public String chat(){
        return "/dev/chat/chat";
    }
/*    @GetMapping("/chat")
    public List<WebSockChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }*/
    
    @GetMapping("/chat/roomList")
    public String rooms(Model model){
        model.addAttribute("rooms",chatService.findAllRoom());
        return "/dev/chat/roomList";
    }

    @GetMapping("/chat/rooms/{id}")
    public String room(@PathVariable String id, Model model){
        WebSockChatRoom room = chatService.findRoomById(id);
        model.addAttribute("room",room);
        return "room";
    }

    @GetMapping("/chat/new")
    public String make(@RequestParam String name){
        String form = new ChatRoomForm();
        model.addAttribute("form",name);
        return "newRoom";
    }

    @PostMapping("/chat/room/new")
    public String makeRoom(ChatRoomForm form){
        chatService.createChatRoom(form.getName());

        return "redirect:/";
    }
}
