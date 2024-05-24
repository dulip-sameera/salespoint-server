package com.salespoint.api.services;

import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.ItemDto;
import com.salespoint.api.entities.ItemEntity;

@Service
public interface ItemService {
    public Iterable<ItemEntity> getAllItems();

    public ItemEntity getItemById(Long id);

    public ItemEntity getItemByName(String name);

    public ItemEntity createItem(ItemDto itemDto);

    public ItemEntity updateItem(Long id, ItemDto itemDto);

    public void deleteItem(Long id);
}
