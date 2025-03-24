package com.example.scheduler;

import com.example.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private final EmailService emailService;

    public EmailScheduler(EmailService emailService) {
        this.emailService = emailService;
    }

    // ✅ Chạy mỗi 10 phút
    @Scheduled(fixedRate = 600000)
    public void checkEmails() {
        System.out.println("🔄 Kiểm tra email...");
        emailService.fetchEmails();
    }
}
