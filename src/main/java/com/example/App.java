package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.ApplicationContext;

import com.example.service.EmailService;
import com.example.listener.EmailListener;

@SpringBootApplication
@EnableScheduling
public class App {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);

        // Bắt đầu lắng nghe email mới khi ứng dụng khởi động
        EmailListener emailListener = context.getBean(EmailListener.class);
        emailListener.startListening();
    }
}
