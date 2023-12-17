package com.vama.vamabackend.models.geolocate;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeoLocateAddressModelResponse {
    private List<AddressModel> suggestions = new ArrayList<>();
}
