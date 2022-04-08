package org.elysian.spring.training.controller;

import org.elysian.spring.training.model.Store;
import org.elysian.spring.training.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elysian.spring.training.config.Roles.ADMIN_ROLE;
import static org.elysian.spring.training.config.Roles.USER_ROLE;

@RestController
@RequestMapping(
        path = "/store"
)
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(final StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "')")
    public ResponseEntity<?> createStore(@RequestBody Store store) {
        storeService.create(store);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "') or hasRole('" + USER_ROLE + "')")
    public Store getStore(@PathVariable final int id) {
        return storeService.get(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "') or hasRole('" + USER_ROLE + "')")
    public List<Store> getAllStores() {
        return StreamSupport.stream(storeService.getAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('" + ADMIN_ROLE + "')")
    public ResponseEntity<?> updateStore(@PathVariable final int id,
                                         @RequestBody Store store) {
        storeService.update(id, store);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
