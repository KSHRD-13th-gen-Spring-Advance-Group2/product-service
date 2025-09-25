package com.khrd.product_service.controller;

import com.khrd.product_service.exception.BadRequestException;
import com.khrd.product_service.model.dto.request.ProductRequest;
import com.khrd.product_service.model.dto.response.ApiResponse;
import com.khrd.product_service.model.dto.response.ProductResponse;
import com.khrd.product_service.model.enumeration.ProductProperty;
import com.khrd.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product Controller")
//@SecurityRequirement(name = "bearerAuth")
public class ProductController extends BaseController{
    private final ProductService productService;

    @Operation(summary = "Get all products {paginated}")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam ProductProperty productProperty,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        try {
            if (page <= 0) throw new BadRequestException("Page index must not be less than zero");
            if (size <= 0) throw new BadRequestException("Page size must not be less than one");

            return null;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Get all products {paginated}")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("productId") UUID productId) {
        try {
            return null;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Create a new product")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest) {
        try {
            return null;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Update product by id")
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable("productId") UUID productId, @RequestBody ProductRequest productRequest) {
        try {
            return null;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Delete product by id")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteBookmark(@PathVariable("productId") UUID productId) {
        try {
            return null;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
