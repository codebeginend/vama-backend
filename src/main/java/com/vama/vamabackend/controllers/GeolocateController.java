package com.vama.vamabackend.controllers;

import com.vama.vamabackend.models.geolocate.AddressResultResponse;
import com.vama.vamabackend.models.geolocate.GeoLocateAddressModelResponse;
import com.vama.vamabackend.services.GeolocateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("geolocate")
@AllArgsConstructor
public class GeolocateController {

    GeolocateService geolocateService;

    @GetMapping(value = "find/address")
    private GeoLocateAddressModelResponse getFindAddress(@RequestParam(name = "latitude") double latitude,
                                                          @RequestParam(name = "longitude") double longitude){
        return geolocateService.locateFindAddress(latitude, longitude);
    }

    @GetMapping(value = "suggest/address")
    private GeoLocateAddressModelResponse getSuggestAddress(@RequestParam(name = "text") String text){
        return geolocateService.suggestAddress(text);
    }

    @GetMapping(value = "find/coordinates")
    private List<AddressResultResponse> getFindAddress(@RequestParam(name = "address") String address){
        return geolocateService.locateFindCoordinates(address);
    }
}
