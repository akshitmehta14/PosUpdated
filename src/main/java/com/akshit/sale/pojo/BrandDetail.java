package com.akshit.sale.pojo;
import javax.persistence.*;

import com.sun.istack.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames =
				{ "brand", "category" }) })
public class BrandDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brand_id;
	@NotNull
	private String brand;
	@NotNull
	private String category;
	public int getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
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