package com.akshit.sale.dto;

import com.akshit.sale.model.ProductData;
import com.akshit.sale.model.ProductForm;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.pojo.Product;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.BrandService;
import com.akshit.sale.service.ProductService;
import com.akshit.sale.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductDto {
	@Autowired
	private ProductService service;
	@Autowired
	private BrandService brandService;

	public void add(ProductForm form) throws ApiException {
		check(form);
		form = normalize(form);
		Product p = convert(form);
		service.add(p);
	}
	public List<ProductData> getAll() {
		return service.getAll();
	}


    public void update(int id, ProductForm form) throws ApiException {
		check(form);
		form = normalize(form);
		Product p = convert(form);
		service.update(id,p);
    }

    public ProductData select(int id) throws ApiException {
		return service.select(id);
    }

    private void check(ProductForm form) throws ApiException {
		if(StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(form.getBrand()) || StringUtil.isEmpty(form.getCategory()) || StringUtil.isEmpty(form.getName())){
			throw new ApiException("One or more fields are empty.");
		}
		if(StringUtil.negative(form.getMrp())){
			throw new ApiException("MRP cannot be negative");
		}
	}
    private ProductForm normalize(ProductForm form){
		form.setBarcode(StringUtil.toLowerCaseTrim(form.getBarcode()));
		form.setBrand(StringUtil.toLowerCaseTrim(form.getBrand()));
		form.setCategory(StringUtil.toLowerCaseTrim(form.getCategory()));
		return form;
	}

    private Product convert(ProductForm form) throws ApiException {
		BrandDetail b = new BrandDetail();
		b.setCategory(form.getCategory());
		b.setBrand(form.getBrand());
		int brandId= brandService.select(b);
		Product converted = new Product();
		converted.setBrand_id(brandId);
		converted.setName(form.getName());
		converted.setMrp(form.getMrp());
		converted.setBarcode(form.getBarcode());
		return converted;
	}
}