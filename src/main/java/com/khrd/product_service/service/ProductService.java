package com.khrd.product_service.service;

import com.khrd.product_service.model.dto.request.ProductRequest;
import com.khrd.product_service.model.dto.response.PagedResponse;
import com.khrd.product_service.model.dto.response.ProductResponse;
import com.khrd.product_service.model.enumeration.ProductProperty;
import org.springframework.data.domain.Sort;

public interface ProductService {
    PagedResponse<ProductResponse> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction);

    ProductResponse getProductById(Long id);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    void deleteProduct(Long id);
}
