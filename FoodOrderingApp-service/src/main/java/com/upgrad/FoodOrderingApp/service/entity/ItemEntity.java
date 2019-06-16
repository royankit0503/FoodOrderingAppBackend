package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "item", schema = "public")
@NamedQueries({
        @NamedQuery(name = "getItemForId", query = "select o from ItemEntity o where o.id = :itemId")
})

public class ItemEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid", unique = true)
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "item_name")
    @NotNull
    @Size(max = 30)
    private String itemName;

    @Column(name = "price")
    @NotNull
    private Integer price;

    @Column(name = "type")
    @NotNull
    @Size(max = 10)
    private String type;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<OrderItemEntity> orderItemList;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<RestaurantItemEntity> restaurantItemList;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<CategoryItemEntity> categoryItemList;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OrderItemEntity> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemEntity> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public List<RestaurantItemEntity> getRestaurantItemList() {
        return restaurantItemList;
    }

    public void setRestaurantItemList(List<RestaurantItemEntity> restaurantItemList) {
        this.restaurantItemList = restaurantItemList;
    }

    public List<CategoryItemEntity> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<CategoryItemEntity> categoryItemList) {
        this.categoryItemList = categoryItemList;
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
