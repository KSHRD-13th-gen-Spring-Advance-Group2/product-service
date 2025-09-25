package com.khrd.product_service.client;

import com.khrd.product_service.model.dto.response.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", path = "/api/v1/categories")
public interface CategoryClient {
    @GetMapping("/{id}")
    ResponseEntity<CategoryResponse> findById(@PathVariable("id") Long id);
}
