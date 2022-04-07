package org.elysian.spring.training.repository;

import org.elysian.spring.training.model.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Integer> {

}
