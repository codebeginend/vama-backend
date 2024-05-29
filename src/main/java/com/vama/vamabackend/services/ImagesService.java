package com.vama.vamabackend.services;

import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.persistence.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ImagesService {

    @Value("${IMAGES_PATH}")
    private String imagesPath;

    ProductsRepository productsRepository;

    public ImagesService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public void init() {
        try {
            Files.createDirectories(Paths.get(imagesPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public ProductsEntity saveForProduct(MultipartFile request, Long productId) {
        ProductsEntity entity = productsRepository.findById(productId).orElse(null);
        if (entity != null){
            if (entity.getLogo() != null){
                if (existFile(entity.getLogo())){
                    try {
                        delete(entity.getLogo());
                    }catch (IOException e){

                    }
                }
            }
            String fileName = saveImage(request, entity.getId() + "_" + entity.getCode() + "_" + LocalDateTime.now().toString() + ".png");
            entity.setLogo(fileName);
            productsRepository.save(entity);
        }
        return entity;
    }

    public String saveImage(MultipartFile file, String fileName) {
        try {
            Files.copy(file.getInputStream(), Paths.get(imagesPath).resolve(fileName));
            return fileName;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource load(Long productId) {
        ProductsEntity entity = productsRepository.findById(productId).orElse(null);
        if (entity == null){
            throw new NullPointerException();
        }
        try {
            Path file = Paths.get(imagesPath).resolve(entity.getLogo());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Resource load(String name) {
        try {
            Path file = Paths.get(imagesPath).resolve(name);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean delete(String fileName) throws IOException {
        File file = Paths.get(imagesPath).resolve(fileName).toFile();
        return FileSystemUtils.deleteRecursively(file);
    }

    public boolean existFile(String fileName){
        File file = Paths.get(imagesPath).resolve(fileName).toFile();
        return file.isFile();
    }
}
