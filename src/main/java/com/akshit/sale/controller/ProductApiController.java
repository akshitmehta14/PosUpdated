package com.akshit.sale.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.akshit.sale.dto.ProductDto;
import com.akshit.sale.model.ProductForm;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.model.ProductData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	@ApiOperation(value = "Shows all products")
	@RequestMapping(path = "/api/product", method = RequestMethod.GET)
	public List<ProductData> add() {
		return productdto.getall();
	}
}
