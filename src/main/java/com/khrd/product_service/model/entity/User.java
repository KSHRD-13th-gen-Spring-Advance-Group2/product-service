package com.khrd.product_service.model.entity;

import com.khrd.product_service.model.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;

    public UserResponse toResponse() {
        return new UserResponse(UUID.fromString(this.id), this.firstName, this.lastName, this.username, this.email, this.imageUrl);
    }
}
