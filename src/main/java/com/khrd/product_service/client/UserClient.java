package com.khrd.product_service.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Developed by ChhornSeyha
 * Date: 27/09/2025
 */

@FeignClient(name = "user-service", url = "http://localhost:9090", path = "/api/v1/users")
public interface UserClient {
}
