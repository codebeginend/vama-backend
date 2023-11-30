package com.vama.vamabackend.controllers;

import com.vama.vamabackend.services.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
@AllArgsConstructor
public class SearchController {

    private SearchService searchService;

    @GetMapping(value = "products/names")
    private List<String> findAllByCategoryId(@RequestParam(name = "searchText") String searchText){
        return searchService.findAllProductNames(searchText);
    }

}
