package com.example.listener;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import jakarta.mail.*;
import jakarta.mail.event.MessageCountAdapter;
import jakarta.mail.event.MessageCountEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.logging.Logger;

@Component
public class EmailListener {
    private static final Logger logger = Logger.getLogger(EmailListener.class.getName());

    @Value("${mail.imap.host}")
    private String host;

    @Value("${mail.imap.port}")
    private String port;

    @Value("${mail.imap.username}")
    private String username;

    @Value("${mail.imap.password}")
    private String password;

    public void startListening() {
        new Thread(this::listenForNewEmails).start();
    }

    private void listenForNewEmails() {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imap");
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", port);
            properties.put("mail.imap.ssl.enable", "true");

            Session session = Session.getInstance(properties);
            IMAPStore store = (IMAPStore) session.getStore("imap");
            store.connect(username, password);

            IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX"); // ✅ Dùng IMAPFolder thay vì Folder
            inbox.open(Folder.READ_ONLY);

            inbox.addMessageCountListener(new MessageCountAdapter() {
                @Override
                public void messagesAdded(MessageCountEvent event) {
                    try {
                        Message[] messages = event.getMessages();
                        for (Message message : messages) {
                            logger.info("📩 Email mới: " + message.getSubject());
                        }
                    } catch (MessagingException e) {
                        logger.severe("❌ Lỗi xử lý email mới: " + e.getMessage());
                    }
                }
            });

            while (!Thread.interrupted()) {
                logger.info("🔄 Đang chờ email mới...");
                inbox.idle(); // ✅ Bây giờ có thể gọi idle()
            }

        } catch (Exception e) {
            logger.severe("❌ Lỗi khi kết nối Gmail: " + e.getMessage());
        }
    }
}
