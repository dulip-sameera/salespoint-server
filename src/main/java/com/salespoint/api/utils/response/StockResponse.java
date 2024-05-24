package com.salespoint.api.utils.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {
    private Long id;
    private Integer qty;
    private Date createdAt;
    private Date updatedAt;
    private ItemResponse item;
}
