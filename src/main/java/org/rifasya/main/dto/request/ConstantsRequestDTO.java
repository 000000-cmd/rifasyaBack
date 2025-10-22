package org.rifasya.main.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConstantsRequestDTO(
        @NotBlank(message = "El código no puede estar vacío.")
        @Size(max = 50, message = "El código no puede tener más de 50 caracteres.")
        String code,

        @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres.")
        String description,

        @NotBlank(message = "El valor no puede estar vacío.")
        String value,

        @NotNull(message = "Debe especificar si el indicador está habilitado.")
        Boolean indicatorEnabled
) {}