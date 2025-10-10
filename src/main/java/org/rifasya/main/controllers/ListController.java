package org.rifasya.main.controllers;

import jakarta.validation.Valid;
import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.services.*;
import org.rifasya.main.services.listService.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    // Injection of all list services
    private final ListCategoryService categoryService;
    private final ListDocumentTypeService documentTypeService;
    private final ListGenderTypeService genderTypeService;
    private final ListRoleService roleTypeService;
    private final ListExternalLotteryService externalLotteryService;
    private final ListDrawMethodService drawMethodService;
    private final ListRaffleModelService raffleModelService;
    private final ListPrizeTypeService prizeTypeService;
    private final ListRaffleRuleService raffleRuleService;

    public ListController(ListCategoryService cs, ListDocumentTypeService dts, ListGenderTypeService gts, ListRoleService rts, ListExternalLotteryService els, ListDrawMethodService dms, ListRaffleModelService rms, ListPrizeTypeService pts, ListRaffleRuleService rrs) {
        this.categoryService = cs;
        this.documentTypeService = dts;
        this.genderTypeService = gts;
        this.roleTypeService = rts;
        this.externalLotteryService = els;
        this.drawMethodService = dms;
        this.raffleModelService = rms;
        this.prizeTypeService = pts;
        this.raffleRuleService = rrs;
    }

    // --- Generic helper methods for the endpoints ---
    private ResponseEntity<List<ListTypeResponseDTO>> getAll(AbstractListService<?, ?, ?> service) {
        return ResponseEntity.ok(service.findAll());
    }
    private ResponseEntity<ListTypeResponseDTO> getById(AbstractListService<?, ?, ?> service, UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
    private ResponseEntity<ListTypeResponseDTO> create(AbstractListService<?, ?, ?> service, ListTypeRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }
    private ResponseEntity<ListTypeResponseDTO> update(AbstractListService<?, ?, ?> service, UUID id, ListTypeRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    private ResponseEntity<Void> delete(AbstractListService<?, ?, ?> service, UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- CRUD for Categories (/api/lists/categories) ---
    @GetMapping("/categories") public ResponseEntity<List<ListTypeResponseDTO>> getAllCategories() { return getAll(categoryService); }
    @GetMapping("/categories/{id}") public ResponseEntity<ListTypeResponseDTO> getCategoryById(@PathVariable UUID id) { return getById(categoryService, id); }
    @PostMapping("/categories") public ResponseEntity<ListTypeResponseDTO> createCategory(@Valid @RequestBody ListTypeRequestDTO dto) { return create(categoryService, dto); }
    @PutMapping("/categories/{id}") public ResponseEntity<ListTypeResponseDTO> updateCategory(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(categoryService, id, dto); }
    @DeleteMapping("/categories/{id}") public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) { return delete(categoryService, id); }

    // --- CRUD for Document Types (/api/lists/document-types) ---
    @GetMapping("/documenttypes") public ResponseEntity<List<ListTypeResponseDTO>> getAllDocumentTypes() { return getAll(documentTypeService); }
    @GetMapping("/documenttypes/{id}") public ResponseEntity<ListTypeResponseDTO> getDocumentTypeById(@PathVariable UUID id) { return getById(documentTypeService, id); }
    @PostMapping("/documenttypes") public ResponseEntity<ListTypeResponseDTO> createDocumentType(@Valid @RequestBody ListTypeRequestDTO dto) { return create(documentTypeService, dto); }
    @PutMapping("/documenttypes/{id}") public ResponseEntity<ListTypeResponseDTO> updateDocumentType(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(documentTypeService, id, dto); }
    @DeleteMapping("/documenttypes/{id}") public ResponseEntity<Void> deleteDocumentType(@PathVariable UUID id) { return delete(documentTypeService, id); }

    // --- CRUD for Gender Types (/api/lists/gender-types) ---
    @GetMapping("/gendertypes") public ResponseEntity<List<ListTypeResponseDTO>> getAllGenderTypes() { return getAll(genderTypeService); }
    @GetMapping("/gendertypes/{id}") public ResponseEntity<ListTypeResponseDTO> getGenderTypeById(@PathVariable UUID id) { return getById(genderTypeService, id); }
    @PostMapping("/gendertypes") public ResponseEntity<ListTypeResponseDTO> createGenderType(@Valid @RequestBody ListTypeRequestDTO dto) { return create(genderTypeService, dto); }
    @PutMapping("/gendertypes/{id}") public ResponseEntity<ListTypeResponseDTO> updateGenderType(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(genderTypeService, id, dto); }
    @DeleteMapping("/gendertypes/{id}") public ResponseEntity<Void> deleteGenderType(@PathVariable UUID id) { return delete(genderTypeService, id); }

    // --- CRUD for Role Types (/api/lists/role-types) ---
    @GetMapping("/roletypes") public ResponseEntity<List<ListTypeResponseDTO>> getAllRoleTypes() { return getAll(roleTypeService); }
    @GetMapping("/roletypes/{id}") public ResponseEntity<ListTypeResponseDTO> getRoleTypeById(@PathVariable UUID id) { return getById(roleTypeService, id); }
    @PostMapping("/roletypes") public ResponseEntity<ListTypeResponseDTO> createRoleType(@Valid @RequestBody ListTypeRequestDTO dto) { return create(roleTypeService, dto); }
    @PutMapping("/roletypes/{id}") public ResponseEntity<ListTypeResponseDTO> updateRoleType(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(roleTypeService, id, dto); }
    @DeleteMapping("/roletypes/{id}") public ResponseEntity<Void> deleteRoleType(@PathVariable UUID id) { return delete(roleTypeService, id); }

    // --- CRUD for External Lotteries (/api/lists/external-lotteries) ---
    @GetMapping("/externallotteries") public ResponseEntity<List<ListTypeResponseDTO>> getAllExternalLotteries() { return getAll(externalLotteryService); }
    @GetMapping("/externallotteries/{id}") public ResponseEntity<ListTypeResponseDTO> getExternalLotteryById(@PathVariable UUID id) { return getById(externalLotteryService, id); }
    @PostMapping("/externallotteries") public ResponseEntity<ListTypeResponseDTO> createExternalLottery(@Valid @RequestBody ListTypeRequestDTO dto) { return create(externalLotteryService, dto); }
    @PutMapping("/externallotteries/{id}") public ResponseEntity<ListTypeResponseDTO> updateExternalLottery(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(externalLotteryService, id, dto); }
    @DeleteMapping("/externallotteries/{id}") public ResponseEntity<Void> deleteExternalLottery(@PathVariable UUID id) { return delete(externalLotteryService, id); }

    // --- CRUD for Draw Methods (/api/lists/draw-methods) ---
    @GetMapping("/drawmethods") public ResponseEntity<List<ListTypeResponseDTO>> getAllDrawMethods() { return getAll(drawMethodService); }
    @GetMapping("/drawmethods/{id}") public ResponseEntity<ListTypeResponseDTO> getDrawMethodById(@PathVariable UUID id) { return getById(drawMethodService, id); }
    @PostMapping("/drawmethods") public ResponseEntity<ListTypeResponseDTO> createDrawMethod(@Valid @RequestBody ListTypeRequestDTO dto) { return create(drawMethodService, dto); }
    @PutMapping("/drawmethods/{id}") public ResponseEntity<ListTypeResponseDTO> updateDrawMethod(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(drawMethodService, id, dto); }
    @DeleteMapping("/drawmethods/{id}") public ResponseEntity<Void> deleteDrawMethod(@PathVariable UUID id) { return delete(drawMethodService, id); }

    // --- CRUD for Raffle Models (/api/lists/raffle-models) ---
    @GetMapping("/rafflemodels") public ResponseEntity<List<ListTypeResponseDTO>> getAllRaffleModels() { return getAll(raffleModelService); }
    @GetMapping("/rafflemodels/{id}") public ResponseEntity<ListTypeResponseDTO> getRaffleModelById(@PathVariable UUID id) { return getById(raffleModelService, id); }
    @PostMapping("/rafflemodels") public ResponseEntity<ListTypeResponseDTO> createRffleModel(@Valid @RequestBody ListTypeRequestDTO dto) { return create(raffleModelService, dto); }
    @PutMapping("/rafflemodels/{id}") public ResponseEntity<ListTypeResponseDTO> updateRaffleModel(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(raffleModelService, id, dto); }
    @DeleteMapping("/rafflemodels/{id}") public ResponseEntity<Void> deleteRaffleModel(@PathVariable UUID id) { return delete(raffleModelService, id); }

    // --- CRUD for Prize Types (/api/lists/prize-types) ---
    @GetMapping("/prizetypes") public ResponseEntity<List<ListTypeResponseDTO>> getAllPrizeTypes() { return getAll(prizeTypeService); }
    @GetMapping("/prizetypes/{id}") public ResponseEntity<ListTypeResponseDTO> getPrizeTypeById(@PathVariable UUID id) { return getById(prizeTypeService, id); }
    @PostMapping("/prizetypes") public ResponseEntity<ListTypeResponseDTO> createPrizeType(@Valid @RequestBody ListTypeRequestDTO dto) { return create(prizeTypeService, dto); }
    @PutMapping("/prizetypes/{id}") public ResponseEntity<ListTypeResponseDTO> updatePrizeType(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(prizeTypeService, id, dto); }
    @DeleteMapping("/prizetypes/{id}") public ResponseEntity<Void> deletePrizeType(@PathVariable UUID id) { return delete(prizeTypeService, id); }

    // --- CRUD for Raffle Rules (/api/lists/raffle-rules) ---
    @GetMapping("/rafflerules") public ResponseEntity<List<ListTypeResponseDTO>> getAllRaffleRules() { return getAll(raffleRuleService); }
    @GetMapping("/rafflerules/{id}") public ResponseEntity<ListTypeResponseDTO> getRaffleRuleById(@PathVariable UUID id) { return getById(raffleRuleService, id); }
    @PostMapping("/rafflerules") public ResponseEntity<ListTypeResponseDTO> createRaffleRule(@Valid @RequestBody ListTypeRequestDTO dto) { return create(raffleRuleService, dto); }
    @PutMapping("/rafflerules/{id}") public ResponseEntity<ListTypeResponseDTO> updateRaffleRule(@PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) { return update(raffleRuleService, id, dto); }
    @DeleteMapping("/rafflerules/{id}") public ResponseEntity<Void> deleteRaffleRule(@PathVariable UUID id) { return delete(raffleRuleService, id); }
}

