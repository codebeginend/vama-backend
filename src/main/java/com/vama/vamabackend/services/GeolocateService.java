package com.vama.vamabackend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vama.vamabackend.models.geolocate.AddressResultResponse;
import com.vama.vamabackend.models.geolocate.GeoLocateAddressModelResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class GeolocateService {

    public GeoLocateAddressModelResponse locateFindAddress(double latitude, double longitude){
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            String url = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/geolocate/address";
            HttpPost httpPost = new HttpPost(url);

            // Установка заголовков
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Token a4479b71a3d10b62479d5f9e42a672e0f26265e7");

            // Установка тела запроса
            String jsonBody = "{\"lat\": "+latitude+", \"lon\": "+longitude+"}";
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            // Выполнение запроса
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Обработка ответа
            if (httpEntity != null) {
                String responseString = EntityUtils.toString(httpEntity);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                GeoLocateAddressModelResponse addressModel = mapper.readValue(responseString, GeoLocateAddressModelResponse.class);
                return addressModel;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GeoLocateAddressModelResponse suggestAddress(String text){
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            String url = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";
            HttpPost httpPost = new HttpPost(url);

            // Установка заголовков
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Token a4479b71a3d10b62479d5f9e42a672e0f26265e7");

            // Установка тела запроса
            String jsonBody = "{\"query\": \"" +text+ "\"}";
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            // Выполнение запроса
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Обработка ответа
            if (httpEntity != null) {
                String responseString = EntityUtils.toString(httpEntity);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                GeoLocateAddressModelResponse addressModel = mapper.readValue(responseString, GeoLocateAddressModelResponse.class);
                return addressModel;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AddressResultResponse> locateFindCoordinates(String address){
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            String url = "https://cleaner.dadata.ru/api/v1/clean/address";
            HttpPost httpPost = new HttpPost(url);

            // Установка заголовков
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Token a4479b71a3d10b62479d5f9e42a672e0f26265e7");
            httpPost.setHeader("X-Secret", "7f09ebbcde3132f1919a14fd9490ee96cf43984e");
            // Установка тела запроса
            String jsonBody = "[ \"" + address + "\" ]";
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            // Выполнение запроса
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Обработка ответа
            if (httpEntity != null) {
                String responseString = EntityUtils.toString(httpEntity);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                TypeReference<List<AddressResultResponse>> typeRef = new TypeReference<List<AddressResultResponse>>() {};
                List<AddressResultResponse> addressModel = mapper.readValue(responseString, typeRef);
                return addressModel;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
