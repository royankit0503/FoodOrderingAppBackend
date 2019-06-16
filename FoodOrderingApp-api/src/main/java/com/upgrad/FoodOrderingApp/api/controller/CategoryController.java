package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/category",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategories() {
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse();

        List<CategoryEntity> categoriesList = categoryService.getAllCategories();
        List<CategoryListResponse> categoryListResponseList = new ArrayList<CategoryListResponse>();
        CategoryListResponse categoryListResponse;
        if (categoriesList != null && !categoriesList.isEmpty()) {
            for(CategoryEntity eachCategoryEntity :categoriesList){
                categoryListResponse = new CategoryListResponse();
                categoryListResponse.setCategoryName(eachCategoryEntity.getCategoryName());
                categoryListResponse.setId(UUID.fromString(eachCategoryEntity.getUuid()));
                categoryListResponseList.add(categoryListResponse);
            }
            categoriesListResponse.setCategories(categoryListResponseList);
        }
        return new ResponseEntity<CategoriesListResponse>(categoriesListResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/category/{categoryId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
     public ResponseEntity<CategoryDetailsResponse> getCategoryById(@PathVariable("categoryId") String categoryUUID) throws CategoryNotFoundException {
        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse();

        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryUUID);
        categoryDetailsResponse.setCategoryName(categoryEntity.getCategoryName());
        categoryDetailsResponse.setId(UUID.fromString(categoryEntity.getUuid()));
        List<ItemList> listOfItemList = new ArrayList<ItemList>();
        ItemList itemList;
        List<CategoryItemEntity> CategoryItemList = categoryEntity.getCategoryItemList();

        for(CategoryItemEntity categoryItemEntity : CategoryItemList){
            ItemEntity itemEntity = categoryItemEntity.getItem();
            itemList = new ItemList();
            itemList.setId(UUID.fromString(itemEntity.getUuid()));
            itemList.setItemName(itemEntity.getItemName());
            itemList.setPrice(itemEntity.getPrice());
            String itemTypeEnumVal = (("0" == itemEntity.getType())? "VEG" : "NON_VEG");
            itemList.setItemType(ItemList.ItemTypeEnum.fromValue(itemTypeEnumVal));
            listOfItemList.add(itemList);
        }
        categoryDetailsResponse.setItemList(listOfItemList);
        return new ResponseEntity<CategoryDetailsResponse>(categoryDetailsResponse, HttpStatus.OK);
    }
}
