package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CategoryEntity> getAllCategories(){
        try {
            return entityManager.createNamedQuery("getAllCategories", CategoryEntity.class).getResultList();
        } catch (NoResultException ex) {
            return null;
        }

    }

    public CategoryEntity getCategoryById(String categoryUUID){
        try {
            return entityManager.createNamedQuery("getCategoryById", CategoryEntity.class).setParameter("categoryUUID", categoryUUID).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

    }
}
