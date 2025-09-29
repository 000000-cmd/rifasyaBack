package org.rifasya.main.controllers;

import lombok.RequiredArgsConstructor;
import org.rifasya.main.mappers.ListTypeMapper;
import org.rifasya.main.models.ListItemModel;
import org.rifasya.main.repositories.listRepositories.ListDocumentTypeRepository;
import org.rifasya.main.repositories.listRepositories.ListGenderTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    private final ListDocumentTypeRepository docTypeRepo;
    private final ListGenderTypeRepository genderTypeRepo;
    private final ListTypeMapper listTypeMapper;

    ListController(ListDocumentTypeRepository docTypeRepo, ListGenderTypeRepository genderTypeRepo, ListTypeMapper listTypeMapper){
        this.docTypeRepo = docTypeRepo;
        this.genderTypeRepo = genderTypeRepo;
        this.listTypeMapper = listTypeMapper;
    }

    @GetMapping("/documenttypes")
    public List<ListItemModel> getDocumentTypes() {
        return listTypeMapper.toListItemModelsDoc(docTypeRepo.findAll());
    }

    @GetMapping("/gendertypes")
    public List<ListItemModel> getGenderTypes() {
        return listTypeMapper.toListItemModelsGender(genderTypeRepo.findAll());
    }
}
