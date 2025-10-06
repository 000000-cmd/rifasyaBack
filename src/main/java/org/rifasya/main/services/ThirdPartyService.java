package org.rifasya.main.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.rifasya.main.dto.request.ThirdPartyRequestDTO;
import org.rifasya.main.dto.request.UserDTO.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.ThirdPartyResponseDTO;
import org.rifasya.main.dto.response.UserDTO.UserResponseDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.mappers.ThirdPartyMapper;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.models.ThirdPartyModel;
import org.rifasya.main.models.UserModel;
import org.rifasya.main.repositories.ThirdPartyRepository;
import org.rifasya.main.repositories.UserRepository;
import org.rifasya.main.repositories.listRepositories.ListDocumentTypeRepository;
import org.rifasya.main.repositories.listRepositories.ListGenderTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ThirdPartyService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ThirdPartyMapper thirdPartyMapper;
    private final ThirdPartyRepository thirdPartyRepository;
    private final ListDocumentTypeRepository docTypeRepo;
    private final ListGenderTypeRepository genderTypeRepo;

    public ThirdPartyService(UserService userService,
                             UserMapper userMapper,
                             ThirdPartyMapper thirdPartyMapper,
                             ThirdPartyRepository thirdPartyRepository,
                             ListDocumentTypeRepository docTypeRepo,
                             ListGenderTypeRepository genderTypeRepo) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.thirdPartyMapper = thirdPartyMapper;
        this.thirdPartyRepository = thirdPartyRepository;
        this.docTypeRepo = docTypeRepo;
        this.genderTypeRepo = genderTypeRepo;
    }

    @Transactional
    public ThirdPartyResponseDTO create(ThirdPartyRequestDTO dto) {

        EmbeddedUserRequestDTO userDto = dto.getUser();
        userDto.setRoleCodes(java.util.Collections.singletonList("TERUSU"));

        // 1️⃣ Crear usuario usando UserService (ahora asignará el rol)
        UserResponseDTO userResponse = userService.createUser(userDto);
        User userEntity = userMapper.responseToEntity(userResponse);

        // 2️⃣ DTO -> Model de tercero
        ThirdPartyModel thirdPartyModel = thirdPartyMapper.requestToModel(dto, docTypeRepo, genderTypeRepo);
        thirdPartyModel.setId(UUID.randomUUID());
        thirdPartyModel.setUser(userMapper.entityToModel(userEntity));

        // 3️⃣ Model -> Entity con repositorios
        ThirdParty thirdPartyEntity = thirdPartyMapper.modelToEntity(
                thirdPartyModel,
                docTypeRepo,
                genderTypeRepo,
                userEntity
        );
        thirdPartyEntity.setUserAudit(userEntity); // auditor

        // 4️⃣ Guardar en BD
        thirdPartyEntity = thirdPartyRepository.save(thirdPartyEntity);

        // 5️⃣ Entity -> Model -> ResponseDTO
        thirdPartyModel = thirdPartyMapper.entityToModel(thirdPartyEntity);
        ThirdPartyResponseDTO responseDTO =
                thirdPartyMapper.modelToResponseDTO(thirdPartyModel, docTypeRepo, genderTypeRepo);

        // 6️⃣ Agregar info del usuario
        responseDTO.setUser(userResponse);

        return responseDTO;
    }

}



