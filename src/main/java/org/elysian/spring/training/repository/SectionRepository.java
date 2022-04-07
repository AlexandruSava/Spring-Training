package org.elysian.spring.training.repository;

import org.elysian.spring.training.model.Section;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends CrudRepository<Section, Integer> {

    Optional<List<Section>> findAllByStoreId(final int storeId);
}
