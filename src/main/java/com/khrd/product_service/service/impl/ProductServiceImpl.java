package com.khrd.product_service.service.impl;

import com.khrd.product_service.exception.NotFoundException;
import com.khrd.product_service.model.dto.request.ProductRequest;
import com.khrd.product_service.model.dto.response.PagedResponse;
import com.khrd.product_service.model.dto.response.ProductResponse;
import com.khrd.product_service.model.entity.Product;
import com.khrd.product_service.model.enumeration.ProductProperty;
import com.khrd.product_service.repository.ProductRepository;
import com.khrd.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public PagedResponse<ProductResponse> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction) {
        return null;
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        return null;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        return null;
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategoryResponse(null);
        product.setUserResponse(null);
        product.setCategoryId(UUID.fromString("2d0eac7b-5351-4444-9ff4-23d6e5360820"));
        product.setUserId(UUID.fromString("84a5b7e8-40c6-4798-9da8-e9b283a94a03"));

        return productRepository.save(product).toResponse();
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        productRepository.delete(product);
    }
}
