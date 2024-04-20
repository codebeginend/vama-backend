package com.vama.vamabackend.controllers;

import com.vama.vamabackend.models.order.*;
import com.vama.vamabackend.services.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.naming.NotContextException;
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

    @PostMapping(value = "change/status")
    private ChangeStatusResponse changeCancelledOrder(@RequestBody ChangeStatusRequest request) {
        return ordersService.changeToCancelledStatus(request.getOrderId(), request.getNextStatus());
    }

    @PostMapping(value = "/admin/change/status")
    private ChangeStatusResponse adminChangeStatusOrder(@RequestBody ChangeStatusRequest request) {
        return ordersService.changeStatus(request.getOrderId(), request.getNextStatus());
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
        return ordersService.findAllOrderItemsByOrderIdMe(orderId);
    }

    @GetMapping(value = "admin/all")
    private List<OrdersAdminResponse> getAllOrdersForAdmin(@RequestParam(required = false, defaultValue = "") String status,
                                                           @RequestParam(required = false, defaultValue = "") String searchText){
        return  ordersService.findAllForAdmin(status, searchText);
    }

    @GetMapping(value = "admin/get")
    private OrdersAdminResponse getOneAdmin(@RequestParam Long orderId){
        return ordersService.findByIdAdmin(orderId);
    }

    @GetMapping(value = "admin/items/all")
    private List<OrderItemsResponse> findAllOrderItemsAdmin(@RequestParam Long orderId){
        return ordersService.findAllOrderItemsByOrderId(orderId);
    }

    @GetMapping(value = "admin/all/clients")
    private List<OrdersAdminResponse> findAllOrderByUserId(@RequestParam Long userId){
        return ordersService.findAllByUserIdForAdmin(userId);
    }
}
