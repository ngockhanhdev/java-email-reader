package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.model.Email;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailStorageService {
    private static final String FILE_PATH = "emails.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmailStorageService() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // ✅ Định dạng JSON đẹp
    }

    public void saveEmail(Email email) {
        List<Email> emails = loadEmails();
        emails.add(email); // ✅ Thêm email mới vào danh sách
        try {
            objectMapper.writeValue(new File(FILE_PATH), emails);
            System.out.println("✅ Email đã được lưu vào JSON: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Email> loadEmails() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Email.class));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
