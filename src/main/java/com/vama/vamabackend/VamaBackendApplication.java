package com.vama.vamabackend;

import com.vama.vamabackend.services.ImagesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class VamaBackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VamaBackendApplication.class, args);
    }

    @Resource
    ImagesService imagesService;

    @Override
    public void run(String... arg) {
        imagesService.init();
    }
}
