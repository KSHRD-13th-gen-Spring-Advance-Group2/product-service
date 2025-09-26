package com.khrd.product_service.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private UUID productId;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private CategoryResponse categoryResponse;
    private UserResponse userResponse;
}