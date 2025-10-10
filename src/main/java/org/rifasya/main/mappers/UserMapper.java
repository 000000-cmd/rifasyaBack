package org.rifasya.main.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rifasya.main.dto.request.User.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.LoginResponseDTO;
import org.rifasya.main.dto.response.User.UserResponseDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.models.UserModel;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserModel requestToModel(EmbeddedUserRequestDTO dto);

    @Mapping(target = "attachment", ignore = true)
    User modelToEntity(UserModel model);

    @Mapping(target = "attachment", ignore = true)
    UserModel entityToModel(User entity);

    UserResponseDTO modelToResponseDTO(UserModel model);

    default User responseToEntity(UserResponseDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setCellular(dto.getCellular());
        // password no viene en el response, se debe asignar aparte si necesario
        return user;
    }

    default LoginResponseDTO toLoginResponseDTO(User user, ThirdParty thirdParty) {
        if (user == null) return null;

        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        if (thirdParty != null) {
            dto.setName(thirdParty.getFirstName() + " " + thirdParty.getFirstLastName());
        } else {
            dto.setName(user.getUsername()); // Si no hay tercero, el nombre es el username
        }

        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(userRole -> userRole.getRole().getCode())
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}



