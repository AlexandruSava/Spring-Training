package org.elysian.spring.training.service;

import org.elysian.spring.training.model.Store;
import org.elysian.spring.training.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(final StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void create(Store store) {
        storeRepository.save(store);
    }

    public Store get(int id) {
        return storeRepository.findById(id)
                              .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public Iterable<Store> getAll() {
        return storeRepository.findAll();
    }

    public void update(int id, Store store) {
        final Store updatedStore = get(id);

        updatedStore.setLocation(store.getLocation());
        updatedStore.setName(store.getName());
        updatedStore.setSections(store.getSections());

        storeRepository.save(updatedStore);
    }
}
