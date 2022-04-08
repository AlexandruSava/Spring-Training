package org.elysian.spring.training.controller;

import org.elysian.spring.training.model.Section;
import org.elysian.spring.training.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elysian.spring.training.config.Roles.ADMIN_ROLE;
import static org.elysian.spring.training.config.Roles.USER_ROLE;

@RestController
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(final SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping(path = "/store/{storeId}/section")
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "')")
    public ResponseEntity<?> createSection(@PathVariable final int storeId,
                                           @RequestBody Section section) {
        sectionService.create(storeId, section);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/section/{id}")
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "') or hasRole('" + USER_ROLE + "')")
    public Section getSection(@PathVariable final int id) {
        return sectionService.get(id);
    }

    @GetMapping(path = "/section")
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "') or hasRole('" + USER_ROLE + "')")
    public List<Section> getAllSections() {
        return StreamSupport.stream(sectionService.getAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @GetMapping("/store/{storeId}/section")
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "') or hasRole('" + USER_ROLE + "')")
    public List<Section> getAllSectionsByStoreId(@PathVariable final int storeId) {
        return StreamSupport.stream(sectionService.getAllByStoreId(storeId).spliterator(), false)
                            .collect(Collectors.toList());
    }

    @PutMapping("/section/{id}")
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "')")
    public ResponseEntity<?> updateSection(@PathVariable final int id,
                                           @RequestBody Section section) {
        sectionService.update(id, section);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/section/{id}")
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "')")
    public ResponseEntity<?> deleteSchedule(@PathVariable final int id) {
        sectionService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
