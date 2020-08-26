package com.litvaj.eshop.model.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderedProductDto {

    @NotNull
    private UUID productId;

    @NotNull
    private Double price;

    @NotNull
    private Integer count;
}
