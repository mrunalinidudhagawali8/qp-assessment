package com.qp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "grocery_items")
public class GroceryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grocery_id")
	private Long grocerySkuId;

	@NotBlank(message = "GroceryName is mandatory")
	private String name;

	private String description;

	@NotNull(message = "GroceryPrice is mandatory")
	private Double price;

	@NotNull(message = "StockQty is mandatory")
	private Integer stockQty;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public Long getGrocerySkuId() {
		return grocerySkuId;
	}
	
	public void setGrocerySkuId(Long grocerySkuId) {
		this.grocerySkuId = grocerySkuId;
	}

	@Override
	public String toString() {
		return "GroceryItem [groceryId=" + grocerySkuId + ", name=" + name + ", description=" + description + ", price="
				+ price + ", stockQty=" + stockQty + "]";
	}

	
}
