package com.salespoint.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.StockDto;
import com.salespoint.api.entities.ItemEntity;
import com.salespoint.api.entities.StockEntity;
import com.salespoint.api.exceptions.customs.item.ItemNotFoundException;
import com.salespoint.api.exceptions.customs.stock.StockNotFoundException;
import com.salespoint.api.repositories.ItemRepository;
import com.salespoint.api.repositories.StockRepository;
import com.salespoint.api.services.StockService;

import jakarta.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Iterable<StockEntity> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public StockEntity getStockById(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException());
    }

    @Override
    public List<StockEntity> getStocksByItem(Long itemId) {

        ItemEntity item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException());

        return stockRepository.findAllByItem(item).orElseThrow(() -> new StockNotFoundException());
    }

    @Override
    @Transactional
    public StockEntity createStock(StockDto stockDto) {

        ItemEntity item = itemRepository.findById(stockDto.getItemId()).orElseThrow(() -> new ItemNotFoundException());

        // calculate the new whole stock of the item
        Integer updatedQty = item.getQty() + stockDto.getQty();

        // create the stock
        StockEntity stock = StockEntity
                .builder()
                .qty(stockDto.getQty())
                .item(item)
                .build();
        StockEntity newStock = stockRepository.save(stock);

        // updated the new quantity
        item.setQty(updatedQty);
        itemRepository.save(item);

        return newStock;
    }

    @Override
    @Transactional
    public StockEntity updateStock(Long id, StockDto stockDto) {
        StockEntity existingStock = stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException());

        ItemEntity item = existingStock.getItem();

        item.setQty(item.getQty() - existingStock.getQty());

        itemRepository.save(item);

        ItemEntity newItem = itemRepository.findById(stockDto.getItemId())
                .orElseThrow(() -> new ItemNotFoundException());

        if (!item.getId().equals(newItem.getId())) {
            item = newItem;
            existingStock.setItem(newItem);
        }

        existingStock.setQty(stockDto.getQty());

        item.setQty(item.getQty() + stockDto.getQty());

        itemRepository.save(item);

        return stockRepository.save(existingStock);

    }

    @Override
    @Transactional
    public void deleteStock(Long id) {
        StockEntity existingStock = stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException());

        ItemEntity item = existingStock.getItem();
        item.setQty(item.getQty() - existingStock.getQty());

        // delete the stock
        stockRepository.delete(existingStock);

        // update the item
        itemRepository.save(item);
    }

}
