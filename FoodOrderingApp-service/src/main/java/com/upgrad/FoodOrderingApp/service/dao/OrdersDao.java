package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrdersDao {
    @PersistenceContext
    private EntityManager entityManager;

    public CouponEntity getCouponByName(String couponName) {
        try {
            return entityManager.createNamedQuery("getCouponDetailsForName", CouponEntity.class).setParameter("couponName", couponName).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<OrdersEntity> getAllOrdersForCustomerId(String customerId) {
        try {
            return entityManager.createNamedQuery("getAllOrdersForCustomerId", OrdersEntity.class).setParameter("customerId", customerId).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ItemEntity getItemForId(int itemId) {
        try {
            return entityManager.createNamedQuery("getItemForId", ItemEntity.class).setParameter("itemId", itemId).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
