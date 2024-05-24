package com.salespoint.api.dtos;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long customerId;
    private Long userId;
    private Boolean orderPaid;
    // itemList<itemId, qty>
    private HashMap<Long, Integer> itemList;
}
