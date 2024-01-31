package com.vama.vamabackend.models.products;

import lombok.Data;

@Data
public class PublishProductRequest {
    private Long productId;
    private boolean isPublish;
}
