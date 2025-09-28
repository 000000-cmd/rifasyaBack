package org.rifasya.main.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rifasya.main.dto.request.UserDTO.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.UserDTO.UserResponseDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.models.UserModel;

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
        user.setUser(dto.getUser());
        user.setMail(dto.getMail());
        user.setCellular(dto.getCellular());
        // password no viene en el response, se debe asignar aparte si necesario
        return user;
    }
}



