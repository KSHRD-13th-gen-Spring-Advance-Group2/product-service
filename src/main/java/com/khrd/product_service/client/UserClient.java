package com.khrd.product_service.client;

import com.khrd.product_service.exception.NotFoundException;
import com.khrd.product_service.model.dto.response.ApiResponse;
import com.khrd.product_service.model.entity.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@FeignClient(name = "auth-service", path = "/api/v1/users")
public interface UserClient {
    @GetMapping
    @CircuitBreaker(name = "product-service", fallbackMethod = "userFallback")
    ResponseEntity<ApiResponse<User>> getUser();

    default ResponseEntity<ApiResponse<User>> userFallback(Throwable throwable) {
        throw new NotFoundException("Auth service is not available. Cannot create category.");
    }
}