package org.jeka;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoWebinar1NoReactApplication {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        ChatClient chatClient = SpringApplication.run(DemoWebinar1NoReactApplication.class, args).getBean("chatClient", ChatClient.class);
        System.out.println(chatClient.prompt().user("Социни сказку про Турна Клапауция из созвездия Омеги 4. Который мог путешествовать по звездам со скоростью света").call().content());
    }
}
