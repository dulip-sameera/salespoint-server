package com.salespoint.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salespoint.api.entities.ItemCategoryEntity;
import com.salespoint.api.services.ItemCategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/item-categories")
public class ItemCategoryController {
    @Autowired
    private ItemCategoryService itemCategoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<Iterable<ItemCategoryEntity>> getAllItemCategories() {

        return ResponseEntity.ok(itemCategoryService.getAllItemCategories());

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<ItemCategoryEntity> getItemCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(itemCategoryService.getItemCategoryById(id));

    }

    @GetMapping("/find/{itemCategoryName}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<ItemCategoryEntity> getItemCategoryByName(@PathVariable String itemCategoryName) {
        return ResponseEntity.ok(itemCategoryService.getItemCategoryByName(itemCategoryName));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<ItemCategoryEntity> createItemCategory(@RequestBody ItemCategoryEntity itemCategory) {
        // remove leading and trailing white spaces
        itemCategory.setName(itemCategory.getName().trim());

        return ResponseEntity.status(HttpStatus.CREATED).body(itemCategoryService.createItemCategory(itemCategory));

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<ItemCategoryEntity> updateItemCategory(@PathVariable Integer id,
            @RequestBody ItemCategoryEntity itemCategory) {
        // remove leading and trailing white spaces
        itemCategory.setName(itemCategory.getName().trim());
        ItemCategoryEntity updatedItemCategory = itemCategoryService.updateItemCategory(id, itemCategory);
        return ResponseEntity.ok(updatedItemCategory);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK')")
    public ResponseEntity<String> deleteItemCategory(@PathVariable Integer id) {
        itemCategoryService.deleteItemCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item Category Deleted");
    }

}
