package com.khrd.product_service.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
public class ProductRequest {
    @Schema(example = "string")
    @NotBlank(message = "Product name is required")
    @Size(min = 3, message = "Product name must be at least 3 characters")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    @Pattern(
            regexp = "^[a-zA-Z][\\da-zA-Z\\s]*$",
            message = "Product name must start with letter"
    )
    private String name;

    @Schema(example = "0")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than zero")
    @DecimalMax(value = "99999999.99", message = "Unit price must not exceed 99999999.99")
    @Digits(integer = 8, fraction = 2, message = "Unit price must have at most 8 digits before the decimal point and 2 after")
    private BigDecimal price;

    @Schema(example = "1")
    @NotNull(message = "Quantity is required")
    @Min(value = 1L, message = "Quantity must be at least 1")
    @Max(value = 99999999L, message = "Quantity must have at most 8 digits")
    private Long quantity;

    @NotNull(message = "Category id is required")
    private UUID categoryId;


}