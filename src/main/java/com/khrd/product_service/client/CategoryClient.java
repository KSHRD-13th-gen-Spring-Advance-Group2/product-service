package com.khrd.product_service.client;

import com.khrd.product_service.model.dto.response.ApiResponse;
import com.khrd.product_service.model.dto.response.CategoryResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "category-service", path = "/api/v1/categories")
public interface CategoryClient {
    @GetMapping("/{categoryId}")
    @CircuitBreaker(name = "product-service", fallbackMethod = "categoryFallback")
    ResponseEntity<ApiResponse<CategoryResponse>> findCategoryById(@PathVariable("categoryId") UUID categoryId);

    default ResponseEntity<ApiResponse<CategoryResponse>> categoryFallback(UUID categoryId, Throwable throwable) {
        ApiResponse<CategoryResponse> response = ApiResponse.<CategoryResponse>builder()
                .payload(CategoryResponse.builder()
                        .categoryId(categoryId)
                        .name("N/A")
                        .description("Unavailable")
                        .build())
                .build();

        return ResponseEntity.ok(response);
    }
}
