package com.akshit.sale.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.akshit.sale.model.BrandForm;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.BrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandApiController {

	@Autowired
	private BrandService service;

	@ApiOperation(value = "Adds a brand")
	@RequestMapping(path = "/api/brand", method = RequestMethod.POST)
	public void add(@RequestBody BrandForm form) throws ApiException {
		BrandDetail p = convert(form);
		service.add(p);
	}

	 @ApiOperation(value = "Updates a brand by ID")
	 @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
	  	public void update(@PathVariable int id,@RequestBody BrandForm f) {
	 	service.update(id,f);
	 }


	@ApiOperation(value = "Selects a brand by ID")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
	public BrandDetail select(@PathVariable int id) {
		return service.select(id);
	}

// use brand data for returning database values
	@ApiOperation(value = "Gets list of all brands")
	@RequestMapping(path = "/api/brand", method = RequestMethod.GET)
	public List<BrandDetail> getAll() {
		List<BrandDetail> list = service.getAll();
		return list;
	}
//
//Add dto
//normalization and lowercase - dto
	private static BrandForm convert(BrandDetail p) {
		BrandForm d = new BrandForm();
		d.setBrand(p.getBrand());
		d.setCategory(p.getCategory());
		return d;
	}

	private static BrandDetail convert(BrandForm f) {
		BrandDetail p = new BrandDetail();
		p.setBrand(f.getBrand());
		p.setCategory(f.getCategory());
		return p;
	}

}

