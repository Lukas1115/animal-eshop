package com.litvaj.eshop.model.request;

import com.litvaj.eshop.model.AnimalCategory;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {

    @NotNull
    private String name;

    @NotNull
    private AnimalCategory animalCategory;

    @NotNull
    private Double price;

    private String description;

    private List<String> imagesUrls;
}
