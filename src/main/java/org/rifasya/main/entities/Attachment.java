package org.rifasya.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Attachments")
public class Attachment {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "IdAttachment")
    private UUID idAttachment;

    @Column(name = "FileName", nullable = false)
    private String fileName;

    @Column(name = "EncryptedData", nullable = true)
    private byte[] encryptedData;

    @Column(name = "IndicatorEnabled", nullable = false)
    private Boolean indicatorEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserAudit")
    @JsonIgnoreProperties({"userAudit"}) // evita bucles infinitos al serializar
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;
}
