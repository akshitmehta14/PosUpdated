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
		service.add(form);
	}
	@Transactional
	public List<ProductData> getall() {
		return service.getall();
	}

	@Transactional
    public void update(int id, ProductForm f) {
		service.update(id,f);
    }
}