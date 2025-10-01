package com.khrd.product_service.repository;

import com.khrd.product_service.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAllByUserId(UUID userId, Pageable pageable);

    Optional<Product> findByProductIdAndUserId(UUID productId, UUID userId);

}
