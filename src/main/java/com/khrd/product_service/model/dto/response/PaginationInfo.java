package com.khrd.product_service.model.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationInfo {
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;

    public static <T> PaginationInfo fromPage(Page<T> page) {
        return new PaginationInfo(
                page.getTotalElements(),
                page.getNumber() + 1, // Make it 1-based for the client
                page.getSize(),
                page.getTotalPages()
        );
    }
}