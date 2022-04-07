package org.elysian.spring.training.service;

import org.elysian.spring.training.model.Product;
import org.elysian.spring.training.model.Section;
import org.elysian.spring.training.repository.ProductRepository;
import org.elysian.spring.training.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository,
                          final SectionRepository sectionRepository) {
        this.productRepository = productRepository;
        this.sectionRepository = sectionRepository;
    }

    public void create(final int sectionId, final Product product) {
        Section section = sectionRepository.findById(sectionId)
                                           .orElseThrow(() -> new IllegalArgumentException("Not found"));
        product.setSection(section);
        productRepository.save(product);
    }

    public Product get(final int id) {
        return productRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    public Iterable<Product> getAllBySectionId(int sectionId) {
        return productRepository.findAllBySectionId(sectionId)
                                .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public void update(final int id, final Product product) {
        final Product updatedProduct = get(id);

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());

        productRepository.save(updatedProduct);
    }

    public void delete(final int id) {
        productRepository.deleteById(id);
    }
}

