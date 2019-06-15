package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "customer", schema = "public")
public class CustomerEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid", unique = true)
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "firstname")
    @NotNull
    @Size(max = 30)
    private String firstname;

    @Column(name = "lastname")
    @Size(max = 30)
    private String lastname;

    @Column(name = "email")
    @Size(max = 50)
    private String email;

    @Column(name = "contact_number", unique = true)
    @NotNull
    @Size(max = 30)
    private String contactNumber;

    @NotNull
    @ToStringExclude
    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    @NotNull
    @Size(max = 255)
    @ToStringExclude
    private String salt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<CustomerAuthEntity> customerAuthTokenList;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<CustomerAddressEntity> customerAddressList;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<OrdersEntity> ordersList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<CustomerAuthEntity> getCustomerAuthTokenList() {
        return customerAuthTokenList;
    }

    public void setCustomerAuthTokenList(List<CustomerAuthEntity> customerAuthTokenList) {
        this.customerAuthTokenList = customerAuthTokenList;
    }

    public List<CustomerAddressEntity> getCustomerAddressList() {
        return customerAddressList;
    }

    public void setCustomerAddressList(List<CustomerAddressEntity> customerAddressList) {
        this.customerAddressList = customerAddressList;
    }

    public List<OrdersEntity> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<OrdersEntity> ordersList) {
        this.ordersList = ordersList;
    }



    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
