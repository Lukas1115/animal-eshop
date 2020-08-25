package com.litvaj.eshop.controller;

import com.litvaj.eshop.model.Product;
import com.litvaj.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
public class ProductControllerTest {

    @SpyBean
    private ProductController productController;

    @MockBean
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setupMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    void getAccount() throws Exception {
        UUID uuid = UUID.randomUUID();

        Product product = new Product();
        product.setId(uuid);

        Mockito.when(productService.getProductById(uuid)).thenReturn(product);

        mockMvc.perform(get("/api/products/" + uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(uuid.toString())));
    }
}
