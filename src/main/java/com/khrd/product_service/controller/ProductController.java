package com.khrd.product_service.controller;
import com.khrd.product_service.exception.BadRequestException;
import com.khrd.product_service.model.dto.request.ProductRequest;
import com.khrd.product_service.model.dto.response.ApiResponse;
import com.khrd.product_service.model.dto.response.PagedResponse;
import com.khrd.product_service.model.dto.response.ProductResponse;
import com.khrd.product_service.model.enumeration.ProductProperty;
import com.khrd.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product Controller")
@SecurityRequirement(name = "bearerAuth")
public class ProductController extends BaseController{
    private final ProductService productService;

    @Operation(summary = "Get all products {paginated}")
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam ProductProperty productProperty,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        try {
            if (page <= 0) throw new BadRequestException("Page index must not be less than zero");
            if (size <= 0) throw new BadRequestException("Page size must not be less than one");

        PagedResponse<ProductResponse> payload =  productService.getAllProducts(page,size,productProperty,sortDirection);
            return responseEntity("Get all products successfully",payload);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("productId") UUID productId) {
        try {
            return responseEntity("Get product successfully",productService.getProductById(productId));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Create a new product")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        try {
            return responseEntity("Create product successfully",productService.createProduct(productRequest));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Update product by id")
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable("productId") UUID productId, @Valid @RequestBody ProductRequest productRequest) {
        try {
            return responseEntity("Update product successfully", productService.updateProduct(productId, productRequest));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Delete product by id")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteBookmark(@PathVariable("productId") UUID productId) {
        try {
            productService.deleteProduct(productId);
            return responseEntity("Deleted product successfully", null);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
