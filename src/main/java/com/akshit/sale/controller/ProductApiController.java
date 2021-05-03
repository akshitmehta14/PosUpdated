package com.akshit.sale.controller;

import java.util.List;

import com.akshit.sale.model.BrandForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.akshit.sale.dto.ProductDto;
import com.akshit.sale.model.ProductForm;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.model.ProductData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

@Api
@RestController
public class ProductApiController {
	@Autowired
	private ProductDto productdto;
	
	@ApiOperation(value = "Adds a product")
	@RequestMapping(path = "/api/product", method = RequestMethod.POST)
	public void add(@RequestBody ProductForm form) throws ApiException {
		productdto.add(form);
	}
	@ApiOperation(value = "Updates a product by ID")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
	public void add(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
		productdto.update(id,f);
	}
	@ApiOperation(value = "Selects a product by ID")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
	public ProductData select(@PathVariable int id) throws ApiException {
		return productdto.select(id);
	}
	@ApiOperation(value = "Shows all products")
	@RequestMapping(path = "/api/product", method = RequestMethod.GET)
	public List<ProductData> add() {
		return productdto.getall();
	}
}
