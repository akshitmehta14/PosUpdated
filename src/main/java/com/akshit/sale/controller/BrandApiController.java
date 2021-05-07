package com.akshit.sale.controller;

import java.util.List;

import com.akshit.sale.dto.BrandDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.akshit.sale.model.BrandForm;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(path = "/api/brand")
public class BrandApiController {

	@Autowired
	private BrandDto dto;

	@ApiOperation(value = "Adds a brand")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody BrandForm form) throws ApiException {
		dto.add(form);
	}

	 @ApiOperation(value = "Updates a brand by ID")
	 @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	 public void update(@PathVariable int id,@RequestBody BrandForm f) throws ApiException {
		 dto.update(id,f);
	 }


	@ApiOperation(value = "Selects a brand by ID")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public BrandDetail select(@PathVariable int id) {
		return dto.select(id);
	}

	@ApiOperation(value = "Gets list of all brands")
	@RequestMapping(method = RequestMethod.GET)
	public List<BrandDetail> getAll() {
		return dto.getAll();
	}
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

