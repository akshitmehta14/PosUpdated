package com.akshit.sale.controller;

import com.akshit.sale.dto.ProductDto;
import com.akshit.sale.model.ProductData;
import com.akshit.sale.model.ProductForm;
import com.akshit.sale.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "api/product")
public class ProductApiController {
	@Autowired
	private ProductDto productDto;
	
	@ApiOperation(value = "Adds a product")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody ProductForm form) throws ApiException {
		productDto.add(form);
	}
	@ApiOperation(value = "Updates a product by ID")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
		productDto.update(id,f);
	}
	@ApiOperation(value = "Selects a product by ID")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ProductData select(@PathVariable int id) throws ApiException {
		return productDto.select(id);
	}
	@ApiOperation(value = "Shows all products")
	@RequestMapping(method = RequestMethod.GET)
	public List<ProductData> getAll() {
		return productDto.getAll();
	}
}
