package com.vama.vamabackend.controllers;

import com.vama.vamabackend.models.order.CreateOrderRequest;
import com.vama.vamabackend.models.order.OrderItemsResponse;
import com.vama.vamabackend.models.order.OrdersResponse;
import com.vama.vamabackend.services.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@AllArgsConstructor
public class OrdersController {

    private OrdersService ordersService;

    @PostMapping(value = "create")
    private void createOrder(@RequestBody CreateOrderRequest request){
        ordersService.createOrder(request);
    }

    @GetMapping(value = "me/all")
    private List<OrdersResponse> getAllMe(){
        return ordersService.findAllMe();
    }

    @GetMapping(value = "me/get")
    private OrdersResponse getOneMe(@RequestParam Long orderId){
        return ordersService.findByIdMe(orderId);
    }

    @GetMapping(value = "me/last")
    private OrdersResponse getLastMe(){
        return ordersService.findLastMe();
    }

    @GetMapping(value = "items/me/all")
    private List<OrderItemsResponse> getOrderItemsMe(@RequestParam Long orderId){
        return ordersService.findAllOrderItemsByOrderId(orderId);
    }

    //    @GetMapping(value = "me/last")
//    private OrdersResponse getLastMe(){
//        return ordersService.findByIdMe(orderId);
//    }
}
