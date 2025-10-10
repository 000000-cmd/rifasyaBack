package org.rifasya.main.services;

import jakarta.transaction.Transactional;
import org.rifasya.main.dto.request.ThirdPartyRequestDTO;
import org.rifasya.main.dto.request.User.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.location.LocationResponseDTO;
import org.rifasya.main.dto.response.ThirdPartyResponseDTO;
import org.rifasya.main.dto.response.User.UserResponseDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.exceptions.BadRequestException;
import org.rifasya.main.exceptions.DuplicateResourceException;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.mappers.ThirdPartyMapper;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.models.ThirdPartyModel;
import org.rifasya.main.repositories.ThirdPartyRepository;
import org.rifasya.main.repositories.UserRepository;
import org.rifasya.main.repositories.location.NeighborhoodRepository;
import org.rifasya.main.repositories.listRepositories.ListDocumentTypeRepository;
import org.rifasya.main.repositories.listRepositories.ListGenderTypeRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.UUID;

@Service
public class ThirdPartyService {

    private final UserService userService;
    private final ThirdPartyRepository thirdPartyRepository;
    private final LocationService locationService;
    private final UserRepository userRepository;
    private final ThirdPartyMapper thirdPartyMapper;
    private final UserMapper userMapper;
    private final ListDocumentTypeRepository docTypeRepo;
    private final ListGenderTypeRepository genderTypeRepo;
    private final ListMapper listMapper;
    private final NeighborhoodRepository neighborhoodRepository;


    public ThirdPartyService(UserService userService, ThirdPartyRepository thirdPartyRepository, LocationService locationService, UserRepository userRepository, ThirdPartyMapper thirdPartyMapper, UserMapper userMapper, ListDocumentTypeRepository docTypeRepo, ListGenderTypeRepository genderTypeRepo, ListMapper listMapper, NeighborhoodRepository neighborhoodRepository) {
        this.userService = userService;
        this.thirdPartyRepository = thirdPartyRepository;
        this.locationService = locationService;
        this.userRepository = userRepository;
        this.thirdPartyMapper = thirdPartyMapper;
        this.userMapper = userMapper;
        this.docTypeRepo = docTypeRepo;
        this.genderTypeRepo = genderTypeRepo;
        this.listMapper = listMapper;
        this.neighborhoodRepository = neighborhoodRepository;
    }

    @Transactional
    public ThirdPartyResponseDTO create(ThirdPartyRequestDTO dto) {
        if (thirdPartyRepository.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new DuplicateResourceException("Ya existe un tercero con el número de documento '" + dto.getDocumentNumber() + "'.");
        }

        EmbeddedUserRequestDTO userDto = dto.getUser();
        if (userDto == null) {
            throw new BadRequestException("La información del usuario es obligatoria para crear un tercero.");
        }
        userDto.setRoleCodes(Collections.singletonList("TERUSU"));

        UserResponseDTO userResponse = userService.createUser(userDto);

        User userEntity = userRepository.findById(userResponse.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el usuario recién creado."));

        ThirdPartyModel thirdPartyModel = thirdPartyMapper.requestToModel(dto, docTypeRepo, genderTypeRepo);
        thirdPartyModel.setId(UUID.randomUUID());
        thirdPartyModel.setUser(userMapper.entityToModel(userEntity));

        ThirdParty thirdPartyEntity = thirdPartyMapper.modelToEntity(
                thirdPartyModel, docTypeRepo, genderTypeRepo, listMapper, userEntity
        );
        thirdPartyEntity.setUserAudit(userEntity);
        thirdPartyEntity = thirdPartyRepository.save(thirdPartyEntity);

        locationService.addOrUpdateLocationToThirdParty(thirdPartyEntity.getId(), dto.getLocation(), userEntity);

        ThirdPartyModel finalThirdPartyModel = thirdPartyMapper.entityToModel(thirdPartyEntity);
        ThirdPartyResponseDTO responseDTO =
                thirdPartyMapper.modelToResponseDTO(finalThirdPartyModel, docTypeRepo, genderTypeRepo);

        responseDTO.setLocation(buildLocationResponse(dto.getLocation().getNeighborhoodCode(), dto.getLocation().getAddress(), dto.getLocation().getAddressComplement()));
        responseDTO.setUser(userResponse);

        return responseDTO;
    }

    private LocationResponseDTO buildLocationResponse(String neighborhoodCode, String address, String complement) {
        return neighborhoodRepository.findByCode(neighborhoodCode).map(neighborhood -> {
            var locationDto = new LocationResponseDTO();
            locationDto.setAddress(address);
            locationDto.setAddressComplement(complement);
            locationDto.setNeighborhood(neighborhood.getName());
            locationDto.setMunicipality(neighborhood.getMunicipality().getName());
            locationDto.setDepartment(neighborhood.getMunicipality().getDepartment().getName());
            locationDto.setCountry(neighborhood.getMunicipality().getDepartment().getCountry().getName());
            return locationDto;
        }).orElseThrow(() -> new ResourceNotFoundException("El barrio/vereda con código '" + neighborhoodCode + "' no fue encontrado al construir la respuesta."));
    }
}


