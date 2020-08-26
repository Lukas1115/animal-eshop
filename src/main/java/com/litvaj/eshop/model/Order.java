package com.litvaj.eshop.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="order_table")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column
    private Double totalPrice;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderedProduct> orderedProducts;

    @Column
    private OffsetDateTime updated;
}
