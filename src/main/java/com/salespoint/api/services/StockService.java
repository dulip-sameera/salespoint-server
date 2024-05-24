package com.salespoint.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.StockDto;
import com.salespoint.api.entities.StockEntity;

@Service
public interface StockService {
    public Iterable<StockEntity> getAllStocks();

    public StockEntity getStockById(Long id);

    public List<StockEntity> getStocksByItem(Long itemId);

    public StockEntity createStock(StockDto stockDto);

    public StockEntity updateStock(Long id, StockDto stockDto);

    public void deleteStock(Long id);
}
