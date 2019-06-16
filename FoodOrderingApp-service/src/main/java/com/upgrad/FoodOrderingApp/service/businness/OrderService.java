package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.OrdersDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Service
public class OrderService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrdersDao ordersDao;

    public CouponEntity getCouponDetails(final String authorizationToken ,String couponName) throws AuthorizationFailedException, CouponNotFoundException{
        CustomerAuthEntity customerAuthEntity = customerService.getCustomerAuthToken(authorizationToken);
        CouponEntity couponEntity ;
        if(customerAuthEntity!= null ){
            if (customerService.isUserSignedIn(customerAuthEntity)) {
                if(!customerService.isUserSesionExpired(customerAuthEntity)){
                    if(couponName!= null && !"".equals(couponName.trim())){
                        couponEntity = ordersDao.getCouponByName(couponName);
                        if(couponEntity !=null){
                            return couponEntity;
                        }
                        else{
                            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
                        }
                    }
                    else{
                        throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
                    }
                }
                else{
                    throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
                }
            }
            else{
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }

        }
        else{
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }
    }

    public List<OrdersEntity> getAllOrdersForCustomerId(final String authorizationToken) throws AuthorizationFailedException{
        CustomerAuthEntity customerAuthEntity = customerService.getCustomerAuthToken(authorizationToken);
        CustomerEntity customerEntity= customerAuthEntity.getCustomer();
        String customerId = customerEntity.getId().toString();
        List<OrdersEntity> orderEntityList = new ArrayList<OrdersEntity>();
        if(customerAuthEntity!= null ){
            if (customerService.isUserSignedIn(customerAuthEntity)) {
                if(!customerService.isUserSesionExpired(customerAuthEntity)){
                    if(customerId!= null && !"".equals(customerId.trim())){
                        orderEntityList = ordersDao.getAllOrdersForCustomerId(customerId);

                        Comparator<OrdersEntity> compareById = (OrdersEntity o1, OrdersEntity o2) -> o2.getDate().compareTo( o1.getDate() );
                        Collections.sort(orderEntityList, compareById);
                    }
                }
                else{
                    throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
                }
            }
            else{
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }
        }
        else{
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }
        return orderEntityList;
    }

    public ItemEntity getItemForId(int itemId) {
        ItemEntity itemEntity = ordersDao.getItemForId(itemId);
        return itemEntity;
    }



}