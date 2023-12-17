package com.vama.vamabackend.models.geolocate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressResultResponse {
    @JsonProperty("source")
    private String source;
    private String result;
    private String postalCode;
    private String country;
    private String region;
    private String cityArea;
    private String cityDistrict;
    private String street;
    private String house;
    @JsonProperty("geo_lat")
    private double geoLat;
    @JsonProperty("geo_lon")
    private double geoLon;
    private int qcGeo;
}