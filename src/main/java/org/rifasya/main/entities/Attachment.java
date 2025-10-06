package org.rifasya.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @Column(name = "IdAttachment")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "FileName")
    private String fileName;

    @Column(name = "ContentType")
    private String contentType;

    @Lob
    @Column(name = "Data", columnDefinition="LONGBLOB")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "IdUser", referencedColumnName = "IdUser")
    private User user;

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}