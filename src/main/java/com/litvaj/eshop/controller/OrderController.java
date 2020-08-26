package com.litvaj.eshop.controller;

import com.litvaj.eshop.model.Order;
import com.litvaj.eshop.model.request.OrderDto;
import com.litvaj.eshop.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("creates order")
    public Order createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }
}
