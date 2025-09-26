package com.khrd.product_service.model.entity;

import com.khrd.product_service.model.dto.response.CategoryResponse;
import com.khrd.product_service.model.dto.response.ProductResponse;
import com.khrd.product_service.model.dto.response.UserResponse;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID productId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", precision = 8)
    private Long quantity;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Transient
    private CategoryResponse categoryResponse;

    @Transient
    private UserResponse userResponse;

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .productId(this.productId)
                .name(this.name)
                .price(this.price)
                .quantity(this.quantity)
                .categoryResponse(categoryResponse)
                .userResponse(userResponse)
                .build();
    }
}
