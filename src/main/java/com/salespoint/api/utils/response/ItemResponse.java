package com.salespoint.api.utils.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private Integer qty;
    private String status;
    private String category;
    private Date createdAt;
    private Date updatedAt;
}
