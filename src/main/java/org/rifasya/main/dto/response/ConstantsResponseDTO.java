package org.rifasya.main.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConstantsResponseDTO(
        UUID id,
        String code,
        String description,
        String value,
        Boolean indicatorEnabled,
        String userAuditUsername,
        LocalDateTime auditDate
) {}