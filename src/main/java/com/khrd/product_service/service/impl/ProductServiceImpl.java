package com.khrd.product_service.service.impl;
import com.khrd.product_service.client.CategoryClient;
import com.khrd.product_service.client.UserClient;
import com.khrd.product_service.exception.NotFoundException;
import com.khrd.product_service.model.dto.request.ProductRequest;
import com.khrd.product_service.model.dto.response.*;
import com.khrd.product_service.model.entity.Product;
import com.khrd.product_service.model.entity.User;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserClient userClient;
    private final CategoryClient categoryClient;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ProductResponse> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page - 1, size, direction, productProperty.getFieldName());
//        fetch all data form entity
        Page<Product> productPage = productRepository.findAllByUserId(getUserId(), pageable);
//  Convert the list of Product entities into a list
        List<ProductResponse> productResponses = productPage.getContent()
                .stream()
                .map(product -> {
                    ProductResponse response = product.toResponse();
                    // fetch category
                    CategoryResponse category = getNonCategory(product.getCategoryId());
                    response.setCategoryResponse(category);
                    // fetch user
                    UserResponse user = getUser();
                    response.setUserResponse(user);
                    return response;

                })
                .toList();
//         Create the Pagination metadata object from the Page.
        PaginationInfo pagination = PaginationInfo.fromPage(productPage);

        return new PagedResponse<>(productResponses, pagination);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) throws NotFoundException {

//find product with ID
        Product existProduct = productRepository.findByProductIdAndUserId(id, getUserId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

//        find category with Id
        CategoryResponse getCategory = getNonCategory(existProduct.getCategoryId());
        if (getCategory == null) {
            throw new NotFoundException("Category not found with id: " + existProduct.getCategoryId());
        }
//        find user with Id
        UserResponse getUser = getUser();
        if (getUser == null) {
            throw new NotFoundException("User not found with id: " + existProduct.getUserId());
        }
//        build response
        return ProductResponse.builder()
                .productId(existProduct.getProductId())
                .name(existProduct.getName())
                .quantity(existProduct.getQuantity())
                .price(existProduct.getPrice())
                .categoryResponse(getCategory)
                .userResponse(getUser)
                .build();
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        // Validate and fetch related entities
        CategoryResponse category = getCategoryById(productRequest.getCategoryId());
        if (category == null) {
            throw new NotFoundException("Category not found with id: " + productRequest.getCategoryId());
        }
        UserResponse user = getUser();
        if (user == null) {
            throw new NotFoundException("User not found with id: " + getUserId());
        }

        // Build product entity
        Product product = Product.builder()
                .name(productRequest.getName().trim())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .categoryId(category.getCategoryId())
                .userId(getUserId())
                .build();

        // Set transient fields
        product.setCategoryResponse(category);
        product.setUserResponse(user);

//        save into db
        Product savedProduct = productRepository.save(product);

        return savedProduct.toResponse();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        Product product = productRepository.findByProductIdAndUserId(id, getUserId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        CategoryResponse category = getCategoryById(productRequest.getCategoryId());
        if (category == null) throw new NotFoundException("Category not found with id: " + id);

        product.setName(productRequest.getName().trim());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategoryResponse(category);
        product.setUserResponse(getUser());
        product.setCategoryId(productRequest.getCategoryId());
        product.setUserId(getUserId());

        return productRepository.save(product).toResponse();
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        Product product = productRepository.findByProductIdAndUserId(id, getUserId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        productRepository.delete(product);
    }

    private CategoryResponse getCategoryById(UUID id) {
        ResponseEntity<ApiResponse<CategoryResponse>> categoryResponse = categoryClient.findCategoryById(id);

        return Objects.requireNonNull(categoryResponse.getBody()).getPayload();
    }

    private UserResponse getUser() {
        ResponseEntity<ApiResponse<User>> user = userClient.getUser();
        return Objects.requireNonNull(user.getBody()).getPayload().toResponse();
    }

    private UUID getUserId() {
        return getUser().getUserId();
    }

    private CategoryResponse getNonCategory(UUID id)
    {
        ResponseEntity<ApiResponse<CategoryResponse>> categoryResponse = categoryClient.findCategoryById(id);
        if (categoryResponse == null) {
            return CategoryResponse.builder()
                    .categoryId(UUID.randomUUID())
                    .name("N/A").description("Unavailable")
                    .build();
        }
        return Objects.requireNonNull(categoryResponse.getBody()).getPayload(); }
}
