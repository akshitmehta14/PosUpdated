package com.akshit.sale.dto;

import java.util.List;

import javax.transaction.Transactional;

import com.akshit.sale.model.BrandForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshit.sale.model.ProductForm;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.ProductService;
import com.akshit.sale.model.ProductData;
@Service
public class ProductDto {
	@Autowired
	private ProductService service;
	
	@Transactional
	public void add(ProductForm form) throws ApiException {
		if(StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(form.getBrand()) || StringUtil.isEmpty(form.getCategory()) || StringUtil.isEmpty(form.getName())){
			throw new ApiException("One or more fields are empty.");
		}
		if(StringUtil.negative(form.getMrp())){
			throw new ApiException("MRP cannot be negative");
		}
		form.setBarcode(StringUtil.toLowerCase(form.getBarcode()));
		form.setBrand(StringUtil.toLowerCase(form.getBrand()));
		form.setCategory(StringUtil.toLowerCase(form.getCategory()));
		service.add(form);
	}
	@Transactional
	public List<ProductData> getall() {
		return service.getall();
	}

	@Transactional
    public void update(int id, ProductForm form) throws ApiException {
		if(StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(form.getBrand()) || StringUtil.isEmpty(form.getCategory()) || StringUtil.isEmpty(form.getName())){
			throw new ApiException("One or more fields are empty.");
		}
		if(StringUtil.negative(form.getMrp())){
			throw new ApiException("MRP cannot be negative");
		}
		service.update(id,form);
    }

    public ProductData select(int id) {
		return service.select(id);
    }
}