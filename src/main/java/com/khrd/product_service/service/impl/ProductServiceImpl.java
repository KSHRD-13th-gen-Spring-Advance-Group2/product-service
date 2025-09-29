package com.khrd.product_service.service.impl;

import com.khrd.product_service.client.CategoryClient;
import com.khrd.product_service.client.UserClient;
import com.khrd.product_service.exception.NotFoundException;
import com.khrd.product_service.model.dto.request.ProductRequest;
import com.khrd.product_service.model.dto.response.*;
import com.khrd.product_service.model.entity.Product;
import com.khrd.product_service.model.enumeration.ProductProperty;
import com.khrd.product_service.repository.ProductRepository;
import com.khrd.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserClient userClient;
    private final CategoryClient categoryClient;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ProductResponse> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page - 1, size, direction, productProperty.getFieldName());
//        fetch all data form entity
        Page<Product> productPage = productRepository.findAll(pageable);
//  Convert the list of Product entities into a list
        List<ProductResponse> productResponses = productPage.getContent()
                .stream()
                .map(Product::toResponse)
                .toList();
//         Create the Pagination metadata object from the Page.
        PaginationInfo pagination = PaginationInfo.fromPage(productPage);

      return new PagedResponse<>(productResponses,pagination);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        Product existProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("can't find with this product " + id));
        return existProduct.toResponse();
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategoryId(UUID.fromString("2d0eac7b-5351-4444-9ff4-23d6e5360820"));
        product.setUserId(UUID.fromString("84a5b7e8-40c6-4798-9da8-e9b283a94a03"));
//        save into db
        Product savedProduct = productRepository.save(product);

        return savedProduct.toResponse();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        CategoryResponse category = getCategoryById(productRequest.getCategoryId());


        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategoryResponse(category);
        product.setUserResponse(null);
        product.setCategoryId(productRequest.getCategoryId());
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

    @Override
    public CategoryResponse getCategoryById(UUID id) {
        ResponseEntity<ApiResponse<CategoryResponse>> categoryResponse = categoryClient.findCategoryById(id);
        CategoryResponse category = Objects.requireNonNull(categoryResponse.getBody()).getPayload();
        if (category == null) throw new NotFoundException("Category not found with id: " + id);

        return category;
    }
}
