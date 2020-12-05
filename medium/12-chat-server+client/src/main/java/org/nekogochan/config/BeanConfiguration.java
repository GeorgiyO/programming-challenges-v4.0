package org.nekogochan.config;

import org.nekogochan.socket.ChatSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    ChatSocket chatSocket() {
        return new ChatSocket();
    }

}
