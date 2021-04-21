package com.akshit.sale.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Inventory {
	@Id
	private int product_id;
	private int quantity;
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
