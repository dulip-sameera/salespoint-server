package com.salespoint.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespoint.api.entities.ItemCategoryEntity;
import com.salespoint.api.exceptions.customs.item.ItemCategoryAlreadyExistsException;
import com.salespoint.api.exceptions.customs.item.ItemCategoryNotFoundException;
import com.salespoint.api.repositories.ItemCategoryRepository;
import com.salespoint.api.services.ItemCategoryService;

@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Override
    public Iterable<ItemCategoryEntity> getAllItemCategories() {
        return itemCategoryRepository.findAll();
    }

    @Override
    public ItemCategoryEntity getItemCategoryById(Integer id) {
        return itemCategoryRepository.findById(id).orElseThrow(() -> new ItemCategoryNotFoundException());
    }

    @Override
    public ItemCategoryEntity getItemCategoryByName(String name) {
        return itemCategoryRepository.findByName(name).orElseThrow(() -> new ItemCategoryNotFoundException());
    }

    @Override
    public ItemCategoryEntity createItemCategory(ItemCategoryEntity itemCategoryEntity) {
        Optional<ItemCategoryEntity> optionalItemCategory = itemCategoryRepository
                .findByName(itemCategoryEntity.getName());

        if (optionalItemCategory.isPresent()) {
            throw new ItemCategoryAlreadyExistsException();
        }

        return itemCategoryRepository.save(itemCategoryEntity);
    }

    @Override
    public ItemCategoryEntity updateItemCategory(Integer id, ItemCategoryEntity itemCategory) {
        ItemCategoryEntity existingItemCategory = itemCategoryRepository.findById(id)
                .orElseThrow(() -> new ItemCategoryNotFoundException());
        existingItemCategory.setName(itemCategory.getName());
        return itemCategoryRepository.save(existingItemCategory);
    }

    @Override
    public void deleteItemCategory(Integer id) {
        ItemCategoryEntity existingItemCategory = itemCategoryRepository.findById(id)
                .orElseThrow(() -> new ItemCategoryNotFoundException());
        itemCategoryRepository.delete(existingItemCategory);
    }

}
