package com.akshit.sale.pojo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Repository;
@Entity
public class BrandDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brandId;
	private String brand;
	private String category;
	public int getBrand_id() {
		return brandId;
	}
	public void setBrand_id(int brand_id) {
		this.brandId = brand_id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String ctgry) {
		this.category = ctgry;
	}
	
}