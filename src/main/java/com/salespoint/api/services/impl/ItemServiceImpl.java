package com.salespoint.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.ItemDto;
import com.salespoint.api.entities.ItemCategoryEntity;
import com.salespoint.api.entities.ItemEntity;
import com.salespoint.api.entities.ItemStatusEntity;
import com.salespoint.api.exceptions.customs.item.ItemAlreadyExistsException;
import com.salespoint.api.exceptions.customs.item.ItemCategoryNotFoundException;
import com.salespoint.api.exceptions.customs.item.ItemNotFoundException;
import com.salespoint.api.exceptions.customs.item.ItemQuantityAvailableException;
import com.salespoint.api.exceptions.customs.item.ItemStatusNotFoundException;
import com.salespoint.api.repositories.ItemCategoryRepository;
import com.salespoint.api.repositories.ItemRepository;
import com.salespoint.api.repositories.ItemStatusRepository;
import com.salespoint.api.services.ItemService;
import com.salespoint.api.utils.enums.ItemStatusEnum;
import com.salespoint.api.utils.enums.UserStatusEnum;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemStatusRepository itemStatusRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Override
    public Iterable<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public ItemEntity getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException());
    }

    @Override
    public ItemEntity getItemByName(String name) {
        return itemRepository.findByName(name).orElseThrow(() -> new ItemNotFoundException());
    }

    @Override
    public ItemEntity createItem(ItemDto itemDto) {

        Optional<ItemEntity> optionalItem = itemRepository.findByName(itemDto.getName());

        if (optionalItem.isPresent()) {
            throw new ItemAlreadyExistsException();
        }

        ItemStatusEntity status = itemStatusRepository.findByName(ItemStatusEnum.ACTIVE)
                .orElseThrow(() -> new ItemStatusNotFoundException());

        ItemCategoryEntity category = itemCategoryRepository.findByName(itemDto.getCategory())
                .orElseThrow(() -> new ItemCategoryNotFoundException());

        ItemEntity item = ItemEntity
                .builder()
                .name(itemDto.getName())
                .itemCategory(category)
                .qty(0)
                .unitPrice(itemDto.getUnitPrice())
                .status(status)
                .build();

        return itemRepository.save(item);
    }

    @Override
    public ItemEntity updateItem(Long id, ItemDto itemDto) {
        ItemEntity existingItem = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException());

        ItemStatusEnum statusEnum = itemDto.getStatus().equals(ItemStatusEnum.ACTIVE.toString()) ? ItemStatusEnum.ACTIVE
                : ItemStatusEnum.DELETE;

        ItemStatusEntity status = itemStatusRepository.findByName(statusEnum)
                .orElseThrow(() -> new ItemStatusNotFoundException());

        ItemCategoryEntity category = itemCategoryRepository.findByName(itemDto.getCategory())
                .orElseThrow(() -> new ItemCategoryNotFoundException());

        existingItem.setName(itemDto.getName());
        existingItem.setUnitPrice(itemDto.getUnitPrice());
        existingItem.setStatus(status);
        existingItem.setItemCategory(category);

        return itemRepository.save(existingItem);
    }

    @Override
    public void deleteItem(Long id) {
        ItemEntity existingItem = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException());

        if (existingItem.getQty() > 0) {
            throw new ItemQuantityAvailableException();
        }

        ItemStatusEntity status = itemStatusRepository.findByName(ItemStatusEnum.DELETE)
                .orElseThrow(() -> new ItemStatusNotFoundException());

        existingItem.setStatus(status);

        itemRepository.save(existingItem);
    }

}
