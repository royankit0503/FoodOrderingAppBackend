package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{couponName}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponDetails(@RequestHeader("authorization") final String authorizationToken,
                                                                  @PathVariable("couponName") String couponName)
            throws AuthorizationFailedException, CouponNotFoundException {
        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse();

        CouponEntity couponEntity = orderService.getCouponDetails(authorizationToken, couponName);
        if (couponEntity != null) {
            couponDetailsResponse.setCouponName(couponEntity.getCouponName());
            couponDetailsResponse.setId(UUID.fromString(couponEntity.getUuid()));
            couponDetailsResponse.setPercent(couponEntity.getPercent());
        }
        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/order/customer",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getallPastOrdersForCustomerId(@RequestHeader("authorization") final String authorizationToken) throws AuthorizationFailedException {
        List<OrdersEntity> ordersEntityList = orderService.getAllOrdersForCustomerId(authorizationToken);

        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();
        List<OrderList> listOfOrderList = new ArrayList<OrderList>();
        OrderList orderList;
        OrderListPayment orderListPayment;
        OrderListCustomer orderListCustomer;
        OrderListCoupon orderListCoupon;
        OrderListAddress orderListAddress;
        OrderListAddressState orderListAddressState;
        List<ItemQuantityResponse> ItemQuantityResponseList = new ArrayList<ItemQuantityResponse>();
        ItemQuantityResponse itemQuantityResponse;
        ItemQuantityResponseItem itemQuantityResponseItem = new ItemQuantityResponseItem();


        if (ordersEntityList != null && !ordersEntityList.isEmpty()) {

            CouponEntity couponEntity;
            PaymentEntity paymentEntity;
            CustomerEntity customerEntity;
            AddressEntity addressEntity;
            OrderItemEntity orderItemEntity;
            ItemEntity itemEntity;
            StateEntity stateEntity;


            for (OrdersEntity eachOrderEntity : ordersEntityList) {
                orderList = new OrderList();
                orderList.setId(UUID.fromString(eachOrderEntity.getUuid()));
                orderList.setBill(eachOrderEntity.getBill());

                couponEntity = eachOrderEntity.getCoupon();
                if (couponEntity != null) {
                    orderListCoupon = new OrderListCoupon();
                    orderListCoupon.setId(UUID.fromString(couponEntity.getUuid()));
                    orderListCoupon.setCouponName(couponEntity.getCouponName());
                    orderListCoupon.setPercent(couponEntity.getPercent());
                    orderList.setCoupon(orderListCoupon);
                }
                orderList.setDate(eachOrderEntity.getDate().toString());

                paymentEntity = eachOrderEntity.getPayment();
                if (paymentEntity != null) {
                    orderListPayment = new OrderListPayment();
                    orderListPayment.setId(UUID.fromString(paymentEntity.getUuid()));
                    orderListPayment.setPaymentName(paymentEntity.getPaymentName());
                    orderList.setPayment(orderListPayment);
                }


                customerEntity = eachOrderEntity.getCustomer();
                if (customerEntity != null) {
                    orderListCustomer = new OrderListCustomer();
                    orderListCustomer.setContactNumber(customerEntity.getContactNumber());
                    orderListCustomer.setEmailAddress(customerEntity.getEmail());
                    orderListCustomer.setFirstName(customerEntity.getFirstname());
                    orderListCustomer.setLastName(customerEntity.getLastname());
                    orderListCustomer.setId(UUID.fromString(customerEntity.getUuid()));
                    orderList.setCustomer(orderListCustomer);
                }

                addressEntity = eachOrderEntity.getAddress();
                if (addressEntity != null) {
                    orderListAddress = new OrderListAddress();
                    orderListAddress.setCity(addressEntity.getCity());
                    orderListAddress.setFlatBuildingName(addressEntity.getFlatBuilNumber());
                    orderListAddress.setId(UUID.fromString(addressEntity.getUuid()));
                    orderListAddress.setLocality(addressEntity.getLocality());
                    orderListAddress.setPincode(addressEntity.getPincode());

                    stateEntity = addressEntity.getState();
                    if (stateEntity != null) {
                        orderListAddressState = new OrderListAddressState();
                        orderListAddressState.setStateName(stateEntity.getStateName());
                        orderListAddressState.setId(UUID.fromString(stateEntity.getStateName()));
                        orderListAddress.setState(orderListAddressState);
                    }
                    orderList.setAddress(orderListAddress);
                }

                List<OrderItemEntity> OrderItemEntityList = eachOrderEntity.getOrderItemList();
                if (OrderItemEntityList != null && !OrderItemEntityList.isEmpty()) {
                    for (OrderItemEntity eachOrderItemEntity : OrderItemEntityList) {
                        itemQuantityResponse = new ItemQuantityResponse();
                        if (eachOrderItemEntity != null) {
//                            itemEntity = orderService.getItemForId(eachOrderItemEntity.getId());
                            itemEntity = eachOrderItemEntity.getItem();
                            if (itemEntity != null) {
                                itemQuantityResponseItem = new ItemQuantityResponseItem();
                                itemQuantityResponseItem.setId(UUID.fromString(itemEntity.getUuid()));
                                itemQuantityResponseItem.setItemName(itemEntity.getItemName());
                                itemQuantityResponseItem.setItemPrice(itemEntity.getPrice());
                                itemQuantityResponseItem.setType(ItemQuantityResponseItem.TypeEnum.fromValue(itemEntity.getType()));
                            }
                            itemQuantityResponse.setItem(itemQuantityResponseItem);
                            itemQuantityResponse.setPrice(eachOrderItemEntity.getPrice());
                            itemQuantityResponse.setQuantity(eachOrderItemEntity.getQuantity());
                        }
                        ItemQuantityResponseList.add(itemQuantityResponse);
                    }
                    orderList.setItemQuantities(ItemQuantityResponseList);
                }
                listOfOrderList.add(orderList);
            }
            customerOrderResponse.setOrders(listOfOrderList);
        }
        return new ResponseEntity<CustomerOrderResponse>(customerOrderResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/order",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveOrder(@RequestBody(required = false) final SaveOrderRequest saveOrderRequest,
                                                                           @RequestHeader("authorization") final String authorizationToken) throws AuthorizationFailedException {
//        List<OrdersEntity> ordersEntityList = orderService.getAllOrdersForCustomerId(authorizationToken);
        OrdersEntity ordersEntity = new OrdersEntity();


        SaveOrderResponse saveOrderResponse = new SaveOrderResponse();
        return new ResponseEntity<SaveOrderResponse>(saveOrderResponse, HttpStatus.OK);

    }
}