package com.vama.vamabackend.models.products;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublishProductResponse {
    private Long productId;
    private boolean isPublish;
}
