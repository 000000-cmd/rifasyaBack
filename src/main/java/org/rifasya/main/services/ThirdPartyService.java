package org.rifasya.main.services;

import jakarta.transaction.Transactional;
import org.rifasya.main.dto.request.ThirdPartyRequestDTO;
import org.rifasya.main.dto.request.UserDTO.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.ThirdPartyResponseDTO;
import org.rifasya.main.dto.response.UserDTO.UserResponseDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.mappers.ThirdPartyMapper;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.models.ThirdPartyModel;
import org.rifasya.main.repositories.ThirdPartyRepository;
import org.rifasya.main.repositories.listRepositories.ListDocumentTypeRepository;
import org.rifasya.main.repositories.listRepositories.ListGenderTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class ThirdPartyService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ThirdPartyMapper thirdPartyMapper;
    private final ThirdPartyRepository thirdPartyRepository;
    private final ListDocumentTypeRepository docTypeRepo;
    private final ListGenderTypeRepository genderTypeRepo;
    private final ListMapper listMapper; // Asegúrate de que este mapper esté inyectado

    public ThirdPartyService(UserService userService,
                             UserMapper userMapper,
                             ThirdPartyMapper thirdPartyMapper,
                             ThirdPartyRepository thirdPartyRepository,
                             ListDocumentTypeRepository docTypeRepo,
                             ListGenderTypeRepository genderTypeRepo,
                             ListMapper listMapper) { // Añadido al constructor
        this.userService = userService;
        this.userMapper = userMapper;
        this.thirdPartyMapper = thirdPartyMapper;
        this.thirdPartyRepository = thirdPartyRepository;
        this.docTypeRepo = docTypeRepo;
        this.genderTypeRepo = genderTypeRepo;
        this.listMapper = listMapper; // Inyectado
    }

    @Transactional
    public ThirdPartyResponseDTO create(ThirdPartyRequestDTO dto) {

        // 1. Preparar y crear el usuario
        EmbeddedUserRequestDTO userDto = dto.getUser();
        if (userDto == null) {
            throw new IllegalArgumentException("La información del usuario es obligatoria para crear un tercero.");
        }
        userDto.setRoleCodes(Collections.singletonList("TERUSU")); // Asignar rol de tercero por defecto

        UserResponseDTO userResponse = userService.createUser(userDto);
        User userEntity = userMapper.responseToEntity(userResponse);

        // 2. Mapear DTO de tercero a Modelo
        // Se pasan los repositorios para que MapStruct los use en sus métodos @Context
        ThirdPartyModel thirdPartyModel = thirdPartyMapper.requestToModel(dto, docTypeRepo, genderTypeRepo);
        thirdPartyModel.setId(UUID.randomUUID());
        thirdPartyModel.setUser(userMapper.entityToModel(userEntity));

        // 3. Mapear Modelo de tercero a Entidad
        // Se pasan los repositorios y el userEntity para que MapStruct los resuelva
        ThirdParty thirdPartyEntity = thirdPartyMapper.modelToEntity(
                thirdPartyModel,
                docTypeRepo,
                genderTypeRepo,
                listMapper, // Se pasa el listMapper para que esté disponible en el contexto
                userEntity
        );
        thirdPartyEntity.setUserAudit(userEntity); // Asignar usuario de auditoría

        // 4. Guardar la entidad del tercero
        thirdPartyEntity = thirdPartyRepository.save(thirdPartyEntity);

        // 5. Mapear la entidad guardada de vuelta a un DTO de respuesta
        ThirdPartyModel finalThirdPartyModel = thirdPartyMapper.entityToModel(thirdPartyEntity);
        ThirdPartyResponseDTO responseDTO =
                thirdPartyMapper.modelToResponseDTO(finalThirdPartyModel, docTypeRepo, genderTypeRepo);

        // 6. Adjuntar la información completa del usuario al DTO de respuesta
        responseDTO.setUser(userResponse);

        return responseDTO;
    }

}



