package org.jeka.service;

import lombok.extern.slf4j.Slf4j;
import org.jeka.model.Chat;
import org.jeka.model.ChatEntry;
import org.jeka.model.Role;
import org.jeka.repository.ChatRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.jeka.model.Role.ASSISTANT;
import static org.jeka.model.Role.USER;

@Service
@Slf4j
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatClient chatClient;

//    @Autowired
//    private ChatService myProxy;

    public List<Chat> getAllChats() {
        return chatRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Chat getChat(Long chatId) {
    return chatRepository.findById(chatId).orElseThrow();
    }

    public Chat createNewChat(String title) {
        Chat chat = Chat.builder().title(title).build();
        return chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    @Transactional
    public void proceedInteraction(Long chatId, String prompt) {
        log.info("Starting proceedInteraction for chatId: {}, prompt: {}", chatId, prompt);

        addChatEntry(chatId, prompt, USER);
        log.info("User message saved");

        String answer = chatClient.prompt().user(prompt).call().content();
        log.info("AI response received: {}", answer);

        addChatEntry(chatId, answer, ASSISTANT);
        log.info("Assistant message saved");
    }

    @Transactional
    public void addChatEntry(Long chatId, String content, Role role) {
        log.info("Adding chat entry - chatId: {}, role: {}, content: {}", chatId, role, content);

        Chat chat = chatRepository.findById(chatId).orElseThrow();
        ChatEntry entry = ChatEntry.builder()
                .content(content)
                .role(role)
                .build();

        chat.addChatEntry(entry);
        Chat savedChat = chatRepository.save(chat);

        log.info("Chat saved with ID: {}, entries count: {}", savedChat.getId(), savedChat.getHistory().size());
    }
}
