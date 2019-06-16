package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<CategoryEntity> getAllCategories(){
        List<CategoryEntity> categoriesList = categoryDao.getAllCategories();
        return categoriesList;
    }
    public CategoryEntity getCategoryById(String categoryUUID)throws CategoryNotFoundException{
        if(categoryUUID != null || !"".equalsIgnoreCase(categoryUUID.trim())){
            CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryUUID);
            if(categoryEntity!= null){
                return categoryEntity;
            }
            else{
                throw new CategoryNotFoundException("CNF-002", "No category by this id");
            }
        }
        else{
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

    }
}
