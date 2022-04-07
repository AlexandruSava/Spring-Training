package org.elysian.spring.training.service;

import org.elysian.spring.training.model.Section;
import org.elysian.spring.training.model.Store;
import org.elysian.spring.training.repository.SectionRepository;
import org.elysian.spring.training.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public SectionService(final SectionRepository sectionRepository,
                          final StoreRepository storeRepository) {
        this.sectionRepository = sectionRepository;
        this.storeRepository = storeRepository;
    }

    public void create(final int storeId, final Section section) {
        Store store = storeRepository.findById(storeId)
                                     .orElseThrow(() -> new IllegalArgumentException("Not found"));
        section.setStore(store);
        sectionRepository.save(section);
    }

    public Section get(int id) {
        return sectionRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public Iterable<Section> getAll() {
        return sectionRepository.findAll();
    }

    public Iterable<Section> getAllByStoreId(int storeId) {
        return sectionRepository.findAllByStoreId(storeId)
                                .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public void update(int id, Section section) {
        final Section updatedSection = get(id);

        updatedSection.setName(section.getName());

        sectionRepository.save(updatedSection);
    }

    public void delete(int id) {
        sectionRepository.deleteById(id);
    }
}
