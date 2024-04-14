package com.qp.entities;

import java.util.Date;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long userId;

	@ElementCollection // Specify ElementCollection for Map collections
	@MapKeyColumn(name = "item_name")
	//Map of GroceryItemId and Quantity
	private Map<Long, Integer> purchasedItemsMap;

	private Date orderDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Map<Long, Integer> getPurchasedItemsMap() {
		return purchasedItemsMap;
	}

	public void setPurchasedItemsMap(Map<Long, Integer> purchasedItemsMap) {
		this.purchasedItemsMap = purchasedItemsMap;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
