package org.elysian.spring.training.repository;

import org.elysian.spring.training.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    Optional<List<Product>> findAllBySectionId(final int sectionId);
}
