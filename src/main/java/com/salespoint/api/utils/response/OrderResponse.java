package com.salespoint.api.utils.response;

import com.salespoint.api.entities.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalPrice;
    private Boolean orderPaid;
    private Long customerId;
    private Date createdAt;
    private Date updatedAt;
    private Long createdBy;
    private List<OrderItemResponse> orderItems;
}
