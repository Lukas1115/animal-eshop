package com.litvaj.eshop.controller;

import com.litvaj.eshop.model.Product;
import com.litvaj.eshop.model.request.ProductDto;
import com.litvaj.eshop.model.request.ProductsDto;
import com.litvaj.eshop.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.litvaj.eshop.controller.ControllerConstants.PRODUCT_ID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ResponseBody
    @RequestMapping(value = PRODUCT_ID, method = RequestMethod.GET)
    @ApiOperation("Get a Product by ID")
    public Product getProduct(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation("get all products by attributes")
    public Page<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            @PageableDefault Pageable pageable) {
        return productService.getProducts(name, min, max, pageable);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("creates products")
    public void getProduct(@RequestBody ProductsDto productsDto) {
        productService.createProducts(productsDto);
    }
}
