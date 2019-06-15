package com.upgrad.FoodOrderingApp.service.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;

import java.util.List;

@Repository
public class PaymentDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PaymentEntity> getAllPaymentTypes() {
        try {
            return entityManager.createNamedQuery("getAllPaymentTypes", PaymentEntity.class).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
