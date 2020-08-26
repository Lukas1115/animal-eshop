package com.litvaj.eshop.model.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsDto {

    @NotNull
    private List<ProductDto> products;
}
