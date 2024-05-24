package com.salespoint.api.services;

import org.springframework.stereotype.Service;

import com.salespoint.api.entities.ItemCategoryEntity;

@Service
public interface ItemCategoryService {

    public Iterable<ItemCategoryEntity> getAllItemCategories();

    public ItemCategoryEntity getItemCategoryById(Integer id);

    public ItemCategoryEntity getItemCategoryByName(String name);

    public ItemCategoryEntity createItemCategory(ItemCategoryEntity itemCategoryEntity);

    public ItemCategoryEntity updateItemCategory(Integer id, ItemCategoryEntity itemCategory);

    public void deleteItemCategory(Integer id);

}
