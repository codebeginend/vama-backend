package com.vama.vamabackend.controllers;

import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.services.ImagesService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("images")
@AllArgsConstructor
public class ImagesController {

    ImagesService imagesService;

    @PostMapping(value = "/admin/product/upload")
    private ProductsEntity uploadImageForProduct(@RequestParam(name = "image", required = false) MultipartFile request,
                                                 @RequestParam(name = "productId") Long productId) {
        return imagesService.saveForProduct(request, productId);
    }

    @GetMapping("/admin/get/product")
    @ResponseBody
    public ResponseEntity<Resource> getAdmin(@RequestParam(name = "productId") Long productId) throws IOException {
        Resource file = imagesService.load(productId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename());
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(file.getFile().length());
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @GetMapping("/get/product")
    @ResponseBody
    public ResponseEntity<Resource> get(@RequestParam(name = "name") String name) throws IOException {
        Resource file = imagesService.load(name);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename());
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(file.getFile().length());
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }
}
