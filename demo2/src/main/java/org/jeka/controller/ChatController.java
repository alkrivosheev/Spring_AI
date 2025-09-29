package org.jeka.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeka.model.Chat;
import org.jeka.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/")
    public String mainPage(Model model) {
        log.info("Loading main page");
        model.addAttribute("chats", chatService.getAllChats());
        return "chat";
    }

    @GetMapping("/chat/{chatId}")
    public String chat(@PathVariable Long chatId, Model model) {
        log.info("Loading chat with ID: {}", chatId);
        model.addAttribute("chats", chatService.getAllChats());
        Chat chat = chatService.getChat(chatId);
        model.addAttribute("chat", chat);
        log.info("Chat loaded with {} messages", chat.getHistory().size());
        return "chat";
    }

    @PostMapping("/chat/new")
    public String newChat(@RequestParam String title) {
        log.info("Creating new chat with title: {}", title);
        Chat chat = chatService.createNewChat(title);
        return "redirect:/chat/" + chat.getId();
    }

    @PostMapping("/chat/{chatId}/delete")
    public String deleteChat(@PathVariable Long chatId) {
        log.info("Deleting chat with ID: {}", chatId);
        chatService.deleteChat(chatId);
        return "redirect:/";
    }

    @PostMapping("/chat/{chatId}/entry")
    public String talkToModel(@PathVariable Long chatId, @RequestParam String prompt) {
        log.info("Received message for chat {}: {}", chatId, prompt);
        chatService.proceedInteraction(chatId, prompt);
        log.info("Message processed for chat {}", chatId);
        return "redirect:/chat/" + chatId;
    }
}