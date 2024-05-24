package com.salespoint.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddItemDto {
    private Long orderId;
    private Long itemId;
    private Integer qty;
}
