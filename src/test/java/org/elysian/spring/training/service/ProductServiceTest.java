package org.elysian.spring.training.service;

import org.elysian.spring.training.model.Product;
import org.elysian.spring.training.model.Section;
import org.elysian.spring.training.repository.ProductRepository;
import org.elysian.spring.training.repository.SectionRepository;
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
public class ProductServiceTest {

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Given there are products, when retrieving all the products, then the products are retrieved correctly")
    public void givenThereAreProducts_whenRetrievingProducts_thenProductsAreRetrievedCorrectly() {
        final List<Product> productList = Arrays.asList(
                new Product(1, "Laptop", 1489.99),
                new Product(2, "Monitor", 678.59)
        );
        when(productRepository.findAll()).thenReturn(productList);

        final List<Product> productsRetrieved = (List<Product>) productService.getAll();

        assertNotNull(productsRetrieved, "The products are null");
        assertEquals(productList.size(), productsRetrieved.size(), "Not all the products were returned");
        productsRetrieved.forEach(product -> {
            assertThat(product.getId(), not(0));
            assertThat("The name must not be null or empty", StringUtils.isNotBlank(product.getName()));
            assertThat("The price must not be 0", product.getPrice(), not(0));
        });
    }

    @Test
    @DisplayName("Given there are no products, when retrieving the products, then no products are retrieved")
    public void givenThereAreNoProducts_whenRetrievingProducts_thenNoProductsAreRetrieved() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        final List<Product> retrievedProducts = (List<Product>) productService.getAll();

        assertNotNull(retrievedProducts);
        assertThat(retrievedProducts.size(), is(0));
    }

    @Test
    @DisplayName("Given there are products, when retrieving a product by id, then the product is retrieved correctly")
    public void givenThereAreProducts_whenRetrievingAProductById_thenTheProductIsRetrievedCorrectly() {
        final int productId = 1;
        final String productName = "Laptop";
        final double productPrice = 2599.99;

        final Product product = new Product(productId, productName, productPrice);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        final Product retrievedProduct = productService.get(1);

        assertNotNull(retrievedProduct);
        assertThat(retrievedProduct.getId(), is(productId));
        assertThat(retrievedProduct.getName(), is(productName));
        assertThat(retrievedProduct.getPrice(), is(productPrice));
    }

    @Test
    @DisplayName("Given there are no products, when retrieving a product by id, then an IllegalArgumentException is thrown")
    public void givenThereAreNoProducts_whenRetrievingAProductById_thenIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> productService.get(10));
    }

    @Test
    @DisplayName("Given a product is saved, when saving the product, then save method is called once and the response is not " +
                 "null or empty")
    public void givenAProductIsSaved_whenSavingTheProduct_thenSaveMethodIsCalledOnceAndResponseIsNotNullOrEmpty() {
        final int sectionId = 1;
        final String sectionName = "IT";

        final Section section = mock(Section.class);
        final Product product = mock(Product.class);

        section.setId(sectionId);
        section.setName(sectionName);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(section));

        productService.create(1, product);

        verify(productRepository, times(1)).save(any(Product.class));
    }
}
