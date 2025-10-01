package com.khrd.product_service.service;

import com.khrd.product_service.exception.NotFoundException;
import com.khrd.product_service.model.dto.request.ProductRequest;
import com.khrd.product_service.model.dto.response.CategoryResponse;
import com.khrd.product_service.model.dto.response.PagedResponse;
import com.khrd.product_service.model.dto.response.ProductResponse;
import com.khrd.product_service.model.enumeration.ProductProperty;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface ProductService {
    PagedResponse<ProductResponse> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction);

    ProductResponse getProductById(UUID id) throws NotFoundException;

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(UUID id, ProductRequest productRequest);

    void deleteProduct(UUID id);
}
