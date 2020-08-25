package com.litvaj.eshop.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private AnimalCategory animalCategory;

    @Column
    private Double price;

    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> imagesUrls;
}
