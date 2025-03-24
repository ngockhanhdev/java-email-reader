package com.example.service;

import com.example.model.Email;
// import com.example.repository.EmailRepository;
import jakarta.mail.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Properties;
import java.util.Arrays;
import java.util.Comparator;

@Service
public class EmailService {
    private final EmailStorageService emailStorageService;

   public EmailService(EmailStorageService emailStorageService) {
       this.emailStorageService = emailStorageService;
   }

    @Value("${mail.imap.host}")
    private String host;
    @Value("${mail.imap.port}")
    private String port;
    @Value("${mail.imap.username}")
    private String username;
    @Value("${mail.imap.password}")
    private String password;

    public void fetchEmails() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imap");
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
        properties.put("mail.imap.ssl.enable", "true");

        try {
            Session session = Session.getInstance(properties);
            Store store = session.getStore("imap");
            System.out.println("📧 Login email: "  + username + password);
            store.connect(username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            int totalMessages = messages.length;

            // ✅ Chỉ lấy 3 email mới nhất
            int startIndex = Math.max(0, totalMessages - 3); // Nếu có ít hơn 3 email, lấy từ email đầu tiên
            Message[] latestMessages = inbox.getMessages(startIndex + 1, totalMessages); // getMessages(start, end) dùng chỉ mục từ 1



            for (Message message : latestMessages) {
                String subject = message.getSubject();
                String content = getTextFromMessage(message);

                // ✅ Lưu vào database
//                 Email email = new Email();
//                 email.setSubject(subject);
//                 email.setBody(content);
//                 emailRepository.save(email);

//                 Lưu vào json
                Email email = new Email(subject, content);
                emailStorageService.saveEmail(email);
                System.out.println("📧 Lưu email: "  + subject);
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            return multipart.getBodyPart(0).getContent().toString();
        }
        return "";
    }
}
