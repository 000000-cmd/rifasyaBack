package org.rifasya.main.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.rifasya.main.dto.request.ThirdPartyRequestDTO;
import org.rifasya.main.dto.response.ThirdPartyResponseDTO;
import org.rifasya.main.entities.ThirdParty;
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

    private final ThirdPartyRepository thirdPartyRepo;
    private final ListDocumentTypeRepository DocTypeRepo;
    private final ListGenderTypeRepository GenderTypeRepo;
    private final UserRepository userRepository;

    public ThirdPartyService(ThirdPartyRepository thirdPartyRepository,
                             ListDocumentTypeRepository DocTypeRepo,
                             ListGenderTypeRepository GenderTypeRepo,
                             UserRepository userRepository) {
        this.thirdPartyRepo = thirdPartyRepository;
        this.DocTypeRepo = DocTypeRepo;
        this.GenderTypeRepo = GenderTypeRepo;
        this.userRepository = userRepository;
    }

    @Transactional
    public ThirdPartyResponseDTO create(ThirdPartyRequestDTO thirdPartyRequestDTO) {
        return ThirdPartyRepoToThirdPartyResponseDTO(thirdPartyRepo.save(ThirdPartyDTOToThirdPartyEntity(thirdPartyRequestDTO)));
    }

    private ThirdParty ThirdPartyDTOToThirdPartyEntity(ThirdPartyRequestDTO thirdPartyReqDTO) {

        return new ThirdParty(UUID.randomUUID(),
                thirdPartyReqDTO.getFirstName(),
                thirdPartyReqDTO.getSecondName(),
                thirdPartyReqDTO.getFirstLastName(),
                thirdPartyReqDTO.getSecondLastName(),
                thirdPartyReqDTO.getDocumentNumber(),
                DocTypeRepo.findByCode(thirdPartyReqDTO.getDocumentCode())
                        .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado")),
                GenderTypeRepo.findByCode(thirdPartyReqDTO.getGenderCode())
                        .orElseThrow(() -> new RuntimeException("Genero no encontrado")),
                userRepository.findById(thirdPartyReqDTO.getIdUser())
                        .orElseThrow(() -> new RuntimeException("Usuario relacionado al tercero no encontrado")),
                true,
                userRepository.findById(thirdPartyReqDTO.getUserAuditId())
                        .orElseThrow(() -> new RuntimeException("Usuario auditor no encontrado")),
                LocalDateTime.now()
        );
    }

    private ThirdPartyResponseDTO ThirdPartyRepoToThirdPartyResponseDTO(ThirdParty thirdPartyEnt) {

        return new ThirdPartyResponseDTO(thirdPartyEnt.getId(),
                thirdPartyEnt.getDocumentType().getName(),
                thirdPartyEnt.getDocumentNumber(),
                thirdPartyEnt.getFirstName(),
                thirdPartyEnt.getSecondName(),
                thirdPartyEnt.getFirstLastName(),
                thirdPartyEnt.getSecondLastName(),
                thirdPartyEnt.getGenderType().getName(),
                thirdPartyEnt.getUser().getId(),
                thirdPartyEnt.getIndicatorEnabled()
        );
    }

}
