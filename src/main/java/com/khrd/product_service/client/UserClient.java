package com.khrd.product_service.client;

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
        ApiResponse<User> response = ApiResponse.<User>builder()
                .payload(User.builder()
                        .id(String.valueOf(UUID.randomUUID()))
                        .firstName("N/A")
                        .lastName("N/A")
                        .username("N/A")
                        .email("N/A")
                        .imageUrl(null)
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}