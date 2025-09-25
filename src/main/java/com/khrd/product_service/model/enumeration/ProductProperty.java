package com.khrd.product_service.model.enumeration;

import lombok.Getter;

@Getter
public enum ProductProperty {
    PRODUCT_ID("productId"),
    NAME("name"),
    QUANTITY("quantity");

    private final String fieldName;

    ProductProperty(String fieldName) {
        this.fieldName = fieldName;
    }
}