package com.litvaj.eshop.service;

import com.litvaj.eshop.exception.EntityNotFoundException;
import com.litvaj.eshop.model.Product;
import com.litvaj.eshop.model.request.ProductDto;
import com.litvaj.eshop.model.request.ProductsDto;
import com.litvaj.eshop.repository.ProductRepository;
import com.litvaj.eshop.repository.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Service for manipulations with Products entities.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;


    /**
     * Map and Save the list of product dtos
     *
     * @param productsDtos to save
     */
    public void createProducts(ProductsDto productsDtos) {
        for (ProductDto productDto : productsDtos.getProducts()) {
            Product product = new Product();

            product.setName(productDto.getName());
            product.setAnimalCategory(productDto.getAnimalCategory());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setImagesUrls(productDto.getImagesUrls());

            createProduct(product);
        }
    }

    /**
     * Save the product entity
     *
     * @param product to save
     * @return saved Product with ID
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Get Product by ID otherwise EntityNotFoundException is thrown.
     *
     * @param productId id of Product
     * @return Product
     */
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s not found", productId)));
    }

    /**
     * Paged list of products with filter by price (min, max) and currency (match by 'starts with')
     * @param name
     * @param minPrice
     * @param maxPrice
     * @param pageable
     * @return page of products
     */
    public Page<Product> getProducts(String name, Double minPrice, Double maxPrice, Pageable pageable) {
        ProductSpecification productSpecification = new ProductSpecification(name, minPrice, maxPrice);
        return productRepository.findAll(productSpecification, pageable);
    }
}
