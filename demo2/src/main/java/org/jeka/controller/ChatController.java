package org.jeka.controller;

import org.jeka.model.Chat;
import org.jeka.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("chats", chatService.getAllChats());
        return "chat";
    }

    @GetMapping("/chat/{chatId}")
    public String chat(@PathVariable Long chatId, Model model) {
        model.addAttribute("chats", chatService.getAllChats());
        model.addAttribute("chat", chatService.getChat(chatId));
        return "chat";
    }

    @PostMapping("/chat/new")
    public String newChat(@RequestParam String title) {
        Chat chat = chatService.createNewChat(title);
        return "redirect:/chat/" + chat.getId();
    }

    @GetMapping("/chat/{chatId}/delete")
    public String deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return "/";
    }

}
