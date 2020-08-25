package com.litvaj.eshop.service;

import com.litvaj.eshop.exception.EntityNotFoundException;
import com.litvaj.eshop.model.AnimalCategory;
import com.litvaj.eshop.model.Product;
import com.litvaj.eshop.repository.ProductRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private javax.persistence.EntityManager em;

    private ProductService productService;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
        productService = new ProductService(productRepository);
    }

    @Test
    public void shouldSaveAndGetProduct() {
        final String name = "Cica";
        final AnimalCategory animalCategory = AnimalCategory.CAT;
        final Double price = 95.99;
        final String desc = "cuddly cat";
        final String url1 = "url1";
        final String ul2 = "url2";

        Product cica = createProduct(name, animalCategory, price, desc, url1, ul2);

        UUID saved_cica_ID = productService.createProduct(cica).getId();

        Product cica_db = productService.getProductById(saved_cica_ID);

        assertEquals(saved_cica_ID, cica_db.getId());
        assertEquals(animalCategory, cica_db.getAnimalCategory());
        assertEquals(price, cica_db.getPrice());
        assertEquals(desc, cica_db.getDescription());
        assertEquals(2, cica_db.getImagesUrls().size());
        assertEquals(url1, cica_db.getImagesUrls().get(0));
        assertEquals(ul2, cica_db.getImagesUrls().get(1));
    }

    @Test
    public void shouldFailForNonExistentProduct() {
        UUID uuid = UUID.randomUUID();

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> productService.getProductById(uuid)
        );

        assertEquals(String.format("Product with id %s not found", uuid), exception.getMessage());
    }

    @Test
    public void shouldSearchOnlyByName() {
        createFiveProductsTestData();

        Page<Product> cats = productService.getProducts("Cica", null, null, Pageable.unpaged());

        assertEquals(3, cats.getTotalElements());
        assertEquals("Cica1", cats.getContent().get(0).getName());
        assertEquals("Cica2", cats.getContent().get(1).getName());
        assertEquals("Cica3", cats.getContent().get(2).getName());

        Page<Product> dogs = productService.getProducts("Do", null, null, Pageable.unpaged());
        assertEquals(1, dogs.getTotalElements());
        assertEquals("Dog1", dogs.getContent().get(0).getName());
    }

    @Test
    public void shouldSearchByNameAndMinMax() {
        createFiveProductsTestData();

        Page<Product> cats = productService.getProducts("Cica", 30D, 40D, Pageable.unpaged());

        assertEquals(1, cats.getTotalElements());
        assertEquals("Cica2", cats.getContent().get(0).getName());
    }

    @Test
    public void shouldSearchOnlyByMinMax() {
        createFiveProductsTestData();

        Page<Product> animals = productService.getProducts(null, 30D, 60D, Pageable.unpaged());

        assertEquals(3, animals.getTotalElements());
        assertEquals("Cica2", animals.getContent().get(0).getName());
        assertEquals("Cica3", animals.getContent().get(1).getName());
        assertEquals("Dog1", animals.getContent().get(2).getName());
    }

    @Test
    public void shouldSearchOnlyByMin() {
        createFiveProductsTestData();

        Page<Product> animals = productService.getProducts(null, 60D, null, Pageable.unpaged());

        assertEquals(1, animals.getTotalElements());
        assertEquals("Orca", animals.getContent().get(0).getName());
    }

    @Test
    public void shouldSearchOnlyByMax() {
        createFiveProductsTestData();

        Page<Product> animals = productService.getProducts(null, null, 48D, Pageable.unpaged());

        assertEquals(2, animals.getTotalElements());
        assertEquals("Cica1", animals.getContent().get(0).getName());
        assertEquals("Cica2", animals.getContent().get(1).getName());
    }



    @Test
    public void shouldNotToLoadLazyVariables() {
        Product cica = createProduct("Cica1", AnimalCategory.CAT, 29.99, "cuddly cat", "url1", "url2");

        Product saved_cica_ID = productService.createProduct(cica);

        // Clears the persistence context
        em.flush();
        em.clear();

        Product cica_db = productService.getProductById(saved_cica_ID.getId());
        assertFalse(Hibernate.isInitialized(cica_db.getImagesUrls()));
    }


    private void createFiveProductsTestData() {
        Product p1 = createProduct("Cica1", AnimalCategory.CAT, 29.99, "cuddly cat", "url1", "url2");
        Product p2 = createProduct("Cica2", AnimalCategory.CAT, 39.99, "cuddly big cat", "url1", "url2");
        Product p3 = createProduct("Cica3", AnimalCategory.CAT, 49.99, "almost tiger", "url1", "url2");
        Product p4 = createProduct("Dog1", AnimalCategory.DOG, 59.99, "cute friend", "url1", "url2");
        Product p5 = createProduct("Orca", AnimalCategory.OTHER, 69.99, "sea killer", "url1", "url2");

        productService.createProduct(p1);
        productService.createProduct(p2);
        productService.createProduct(p3);
        productService.createProduct(p4);
        productService.createProduct(p5);
    }

    private Product createProduct(String name, AnimalCategory animalCategory, Double price, String desc, String url1, String url2) {
        Product product = new Product();

        product.setName(name);
        product.setAnimalCategory(animalCategory);
        product.setPrice(price);
        product.setDescription(desc);
        product.setImagesUrls(Arrays.asList(url1, url2));

        return product;
    }

}
