package org.elysian.spring.training.controller;

import org.elysian.spring.training.model.Product;
import org.elysian.spring.training.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/section/{sectionId}/product")
    public ResponseEntity<?> createProduct(@PathVariable final int sectionId,
                                           @RequestBody Product product) {
        productService.create(sectionId, product);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable final int id) {
        return productService.get(id);
    }

    @GetMapping(path = "/product")
    public List<Product> getAllProducts() {
        return StreamSupport.stream(productService.getAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @GetMapping("/section/{sectionId}/product")
    public List<Product> getAllProductsBySectionId(@PathVariable final int sectionId) {
        return StreamSupport.stream(productService.getAllBySectionId(sectionId).spliterator(), false)
                            .collect(Collectors.toList());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable final int id,
                                           @RequestBody Product product) {
        productService.update(id, product);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable final int id) {
        productService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
