package org.rifasya.main.dto.response.ListResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListGenderTypesResponseDTO {

    private UUID idList;
    private String code;
    private String name;
    private Integer order;
    private Boolean indicatorEnabled;
    private LocalDateTime auditDate;
}
