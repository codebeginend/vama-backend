package com.vama.vamabackend.services;

import com.vama.vamabackend.persistence.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class SearchService {

    private ProductsRepository productsRepository;


    public List<String> findAllProductNames(String searchText){
        if (searchText != ""){
            searchText = searchText.toUpperCase(Locale.ROOT) + '%';
            return productsRepository.findAllLike(searchText);
        }else {
            return new ArrayList<>();
        }
    }
}
