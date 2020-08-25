package com.litvaj.eshop.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class OrderedProduct {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column
    private UUID productId;

    @Column
    private Double price;

    @Column
    private Integer count;
}
