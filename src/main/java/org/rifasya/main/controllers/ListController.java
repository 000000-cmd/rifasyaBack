package org.rifasya.main.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.rifasya.main.components.ListServiceFactory;
import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListRegistryResponseDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.services.ListRegistryService;
import org.rifasya.main.services.AbstractListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para gestionar de forma dinámica todas las entidades de tipo "lista".
 * Utiliza un ListServiceFactory para delegar las operaciones al servicio correspondiente
 * basándose en el nombre de la lista proporcionado en la URL.
 */
@Slf4j
@RestController
@RequestMapping("/api/lists")
public class ListController {

    /**
     * El factory se encarga de proveer la instancia de servicio correcta (Category, DocumentType, etc.).
     */
    private final ListServiceFactory serviceFactory;

    /**
     * El servicio para gestionar los metadatos de las listas (el registro de listas).
     */
    private final ListRegistryService listRegistryService;

    /**
     * El constructor ahora es mucho más simple. Solo necesita el factory y el servicio de registro.
     * Ya no es necesario inyectar cada servicio de lista individualmente.
     */
    public ListController(ListServiceFactory serviceFactory, ListRegistryService listRegistryService) {
        this.serviceFactory = serviceFactory;
        this.listRegistryService = listRegistryService;
    }

    /**
     * Método de ayuda privado que utiliza el factory para obtener el servicio
     * correspondiente al {listName} de la URL.
     * Si no se encuentra, lanza una excepción ResourceNotFoundException.
     */
    private AbstractListService<?, ?, ?> getServiceOrThrow(String listName) {
        return serviceFactory.getService(listName)
                .orElseThrow(() -> new ResourceNotFoundException("La lista '" + listName + "' no es válida o no existe."));
    }

    // --- ENDPOINTS CRUD 100% DINÁMICOS ---
    // Estos 6 endpoints ahora manejan TODAS las operaciones para CUALQUIER tipo de lista.

    @GetMapping("/{listName}")
    public ResponseEntity<List<ListTypeResponseDTO>> getAll(@PathVariable String listName) {
        return ResponseEntity.ok(getServiceOrThrow(listName).findAll());
    }

    @GetMapping("/{listName}/{id}")
    public ResponseEntity<ListTypeResponseDTO> getById(@PathVariable String listName, @PathVariable UUID id) {
        return ResponseEntity.ok(getServiceOrThrow(listName).findById(id));
    }

    @PostMapping("/{listName}")
    public ResponseEntity<ListTypeResponseDTO> create(@PathVariable String listName, @Valid @RequestBody ListTypeRequestDTO dto) {
        return new ResponseEntity<>(getServiceOrThrow(listName).create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{listName}/{id}")
    public ResponseEntity<ListTypeResponseDTO> update(@PathVariable String listName, @PathVariable UUID id, @Valid @RequestBody ListTypeRequestDTO dto) {
        return ResponseEntity.ok(getServiceOrThrow(listName).update(id, dto));
    }

    @DeleteMapping("/{listName}/{id}")
    public ResponseEntity<Void> delete(@PathVariable String listName, @PathVariable UUID id) {
        getServiceOrThrow(listName).delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{listName}/reorder")
    public ResponseEntity<Void> reorder(@PathVariable String listName, @RequestBody List<UUID> orderedIds) {
        getServiceOrThrow(listName).updateOrder(orderedIds);
        return ResponseEntity.ok().build();
    }

    // --- Endpoints para el Registro de Listas (Metadata) ---
    // Estos se mantienen ya que gestionan una entidad diferente.

    @GetMapping("/listregistry")
    public ResponseEntity<List<ListRegistryResponseDTO>> getAllListRegistry() {
        return ResponseEntity.ok(listRegistryService.findAll());
    }

    @GetMapping("/listregistry/{id}")
    public ResponseEntity<ListRegistryResponseDTO> getListRegistryById(@PathVariable UUID id) {
        return ResponseEntity.ok(listRegistryService.findById(id));
    }
}
