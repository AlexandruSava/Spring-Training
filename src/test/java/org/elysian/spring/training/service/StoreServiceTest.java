package org.elysian.spring.training.service;

import org.elysian.spring.training.model.Store;
import org.elysian.spring.training.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    @DisplayName("Given there are stores, when retrieving all the stores, then the stores are retrieved correctly")
    public void givenThereAreStores_whenRetrievingAllTheStores_thenStoresAreRetrievedCorrectly() {
        final List<Store> storeList = Arrays.asList(
                new Store(1, "Auchan", "Calea Aradului, Timisoara"),
                new Store(2, "Decathlon", "Calea Lipovei, Timisoara")
        );
        when(storeRepository.findAll()).thenReturn(storeList);

        final List<Store> storesRetrieved = (List<Store>) storeService.getAll();

        assertNotNull(storesRetrieved, "The stores are null");
        assertEquals(storeList.size(), storesRetrieved.size());
        storesRetrieved.forEach(store -> {
            assertThat(store.getId(), not(0));
            assertThat("The name must not be null or empty", StringUtils.isNotBlank(store.getName()));
            assertThat("The location must no be null or empty", StringUtils.isNotBlank(store.getLocation()));
        });
    }

    @Test
    @DisplayName("Given there are no stores, when retrieving the stores, then no stores are retrieved")
    public void givenThereAreNoStores_whenRetrievingTheStores_thenNoStoresAreRetrieved() {
        when(storeRepository.findAll()).thenReturn(new ArrayList<>());

        final List<Store> retrievedStores = (List<Store>) storeService.getAll();

        assertNotNull(retrievedStores);
        assertThat(retrievedStores.size(), is(0));
    }

    @Test
    @DisplayName("Given there are stores, when retrieving a store by id, then the store is retrieved correctly")
    public void givenThereAreStores_whenRetrievingAStoreById_thenTheStoreIsRetrievedCorrectly() {
        final int storeId = 1;
        final String storeName = "Lidl";
        final String storeLocation = "Calea Torontalului, Timisoara";

        final Store store = new Store(storeId, storeName, storeLocation);

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        final Store retrievedStore = storeService.get(storeId);

        assertNotNull(retrievedStore);
        assertThat(retrievedStore.getId(), is(storeId));
        assertThat(retrievedStore.getName(), is(storeName));
        assertThat(retrievedStore.getLocation(), is(storeLocation));
    }

    @Test
    @DisplayName("Given there are no stores, when retrieving a store by id, then an IllegalArgumentException is thrown")
    public void givenThereAreNoStores_whenRetrievingAStoreById_thenAnIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> storeService.get(10));
    }

    @Test
    @DisplayName("Given a store is saved, when saving the product, then save method is called once")
    public void givenAStoreIsSaved_whenSavingTheProduct_thenSaveMethodIsCalledOnce() {
        final Store store = mock(Store.class);

        storeService.create(store);

        verify(storeRepository, times(1)).save(any(Store.class));
    }
}
