package com.vama.vamabackend.controllers;

import com.vama.vamabackend.models.clients.ClientsAdminResponse;
import com.vama.vamabackend.services.ClientsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("clients")
@AllArgsConstructor
public class AdminClientsController {
    private ClientsService clientsService;


    @GetMapping(value = "admin/all")
    public List<ClientsAdminResponse> findAllForAdmin(@RequestParam(required = false, defaultValue = "") String searchText){
        return clientsService.findAllClientsForAdmin(searchText);
    }

    @GetMapping(value = "admin/details")
    public ClientsAdminResponse findDetailsForAdmin(@RequestParam Long userId){
        return clientsService.findDetailsByIdForAdmin(userId);
    }
}
