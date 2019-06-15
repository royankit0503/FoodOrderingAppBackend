package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/payment",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentListResponse> getPaymentMethods(@RequestHeader("authorization") final String authorizationToken) throws AuthorizationFailedException {
        List<PaymentEntity> paymentEntityList = paymentService.getAllPaymentMethods(authorizationToken);
        PaymentResponse paymentResponse;
        PaymentListResponse paymentListResponse = new PaymentListResponse();

       for(PaymentEntity paymentEntity : paymentEntityList){
           paymentResponse = new PaymentResponse();
           paymentResponse.setId(UUID.fromString(paymentEntity.getUuid()));
           paymentResponse.setPaymentName(paymentEntity.getPaymentName());
           paymentListResponse.addPaymentMethodsItem(paymentResponse);
       }
        return new ResponseEntity<PaymentListResponse>(paymentListResponse, HttpStatus.OK);
    }
}
