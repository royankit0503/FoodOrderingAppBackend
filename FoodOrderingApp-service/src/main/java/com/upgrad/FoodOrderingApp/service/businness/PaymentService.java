package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private CustomerBusinessService customerBusinessService;

    @Autowired
    private PaymentDao paymentDao;

    public List<PaymentEntity> getAllPaymentMethods(final String authorizationToken) {

        CustomerAuthEntity customerAuthEntity = customerBusinessService.getCustomerAuthToken(authorizationToken);
        List<PaymentEntity> paymentEntityList = new ArrayList<PaymentEntity>();
        if(customerAuthEntity!= null ){
            if (customerBusinessService.isUserSignedIn(customerAuthEntity)) {
                paymentEntityList = paymentDao.getAllPaymentTypes();
            }

        }
        return paymentEntityList;
    }
}