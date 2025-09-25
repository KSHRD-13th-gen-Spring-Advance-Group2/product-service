package com.khrd.product_service.service.impl;

import com.khrd.product_service.model.dto.request.ProductRequest;
import com.khrd.product_service.model.dto.response.PagedResponse;
import com.khrd.product_service.model.dto.response.ProductResponse;
import com.khrd.product_service.model.enumeration.ProductProperty;
import com.khrd.product_service.repository.ProductRepository;
import com.khrd.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public PagedResponse<ProductResponse> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction) {
        return null;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return null;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
