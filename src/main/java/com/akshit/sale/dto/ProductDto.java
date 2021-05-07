package com.akshit.sale.dto;

import java.util.List;

import javax.transaction.Transactional;

import com.akshit.sale.model.BrandForm;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.pojo.Product;
import com.akshit.sale.service.BrandService;
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
	@Autowired
	private BrandService brandservice;
	
	@Transactional
	public void add(ProductForm form) throws ApiException {
		if(StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(form.getBrand()) || StringUtil.isEmpty(form.getCategory()) || StringUtil.isEmpty(form.getName())){
			throw new ApiException("One or more fields are empty.");
		}
		if(StringUtil.negative(form.getMrp())){
			throw new ApiException("MRP cannot be negative");
		}
		form = normalize(form);
		Product p = convert_check(form);
		service.add(p);
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
		form = normalize(form);
		Product p = convert_check(form);
		service.update(id,p);
    }

    public ProductData select(int id) {
		return service.select(id);
    }

    protected ProductForm normalize(ProductForm form){
		form.setBarcode(StringUtil.toLowerCase(form.getBarcode()));
		form.setBrand(StringUtil.toLowerCase(form.getBrand()));
		form.setCategory(StringUtil.toLowerCase(form.getCategory()));
		return form;
	}

    protected Product convert_check(ProductForm form) throws ApiException {
		BrandDetail b = new BrandDetail();
		b.setCategory(form.getCategory());
		b.setBrand(form.getBrand());
		int brandId= brandservice.select(b);
		Product converted = new Product();
		converted.setBrand_id(brandId);
		converted.setName(form.getName());
		converted.setMrp(form.getMrp());
		converted.setBarcode(form.getBarcode());
		return converted;
	}
}