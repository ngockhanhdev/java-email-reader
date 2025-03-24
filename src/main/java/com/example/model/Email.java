package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Email {
    @JsonProperty("subject")
    private String subject;

    @JsonProperty("body")
    private String body;

    public Email() {}

    public Email(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}




// package com.example.email.model;
//
// import jakarta.persistence.*;
//
// @Entity
// @Table(name = "emails")
// public class Email {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//
//     private String subject;
//     @Lob  // ✅ Thêm annotation này để lưu nội dung dài
//     @Column(columnDefinition = "LONGTEXT")  // ✅ Đặt kiểu dữ liệu cho MySQL
//     private String body;
//
//     // ✅ Thêm getter & setter
//     public Long getId() {
//         return id;
//     }
//
//     public void setId(Long id) {
//         this.id = id;
//     }
//
//     public String getSubject() {
//         return subject;
//     }
//
//     public void setSubject(String subject) {
//         this.subject = subject;
//     }
//
//     public String getBody() {
//         return body;
//     }
//
//     public void setBody(String body) {
//         this.body = body;
//     }
// }




