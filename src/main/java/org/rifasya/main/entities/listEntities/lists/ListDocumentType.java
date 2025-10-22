package org.rifasya.main.entities.listEntities.lists;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.rifasya.main.entities.User;
import org.rifasya.main.entities.listEntities.ListItemBase;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "listdocumenttypes")
public class ListDocumentType  extends ListItemBase {}