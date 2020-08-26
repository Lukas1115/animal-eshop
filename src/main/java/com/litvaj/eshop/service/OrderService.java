package com.litvaj.eshop.service;

import com.litvaj.eshop.model.Order;
import com.litvaj.eshop.model.OrderedProduct;
import com.litvaj.eshop.model.request.OrderDto;
import com.litvaj.eshop.model.request.OrderedProductDto;
import com.litvaj.eshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for manipulations with Order entities.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Creates a Order
     * @param orderDto
     * @return
     */
    public Order createOrder(OrderDto orderDto) {
        if (CollectionUtils.isEmpty(orderDto.getOrderedProducts())) {
            throw new IllegalArgumentException("List of ordered product is empty.");
        }

        Order order = mapToOrderEntity(orderDto);

        return orderRepository.save(order);
    }

    private Order mapToOrderEntity(OrderDto orderDto) {
        Order order = new Order();
        List<OrderedProduct> orderedProductList = new ArrayList<>();
        Double totalPrice = 0D;
        for (OrderedProductDto orderedProduct_dto : orderDto.getOrderedProducts()) {
            totalPrice += orderedProduct_dto.getPrice();
            OrderedProduct op = new OrderedProduct();
            op.setProductId(orderedProduct_dto.getProductId());
            op.setPrice(orderedProduct_dto.getPrice());
            op.setCount(orderedProduct_dto.getCount());
            orderedProductList.add(op);
        }
        order.setOrderedProducts(orderedProductList);
        order.setTotalPrice(totalPrice);
        order.setUpdated(OffsetDateTime.now());
        return order;
    }

}
