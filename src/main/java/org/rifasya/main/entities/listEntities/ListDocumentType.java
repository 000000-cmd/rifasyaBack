package org.rifasya.main.entities.listEntities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.rifasya.main.entities.User;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "listdocumenttypes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ListDocumentType {

    @Id
    @Column(name = "IdList")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "Code", nullable = false)
    private String code;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "`Order`",  nullable = false)
    private Integer order;

    @Column(name = "IndicatorEnabled", nullable = false)
    private Boolean indicatorEnabled;

    // Relación con Users (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserAudit")
    @JsonIgnoreProperties({"userAudit"})
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;
}
