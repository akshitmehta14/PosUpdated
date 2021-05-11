package com.akshit.sale.controller;

import com.akshit.sale.dto.BrandDto;
import com.akshit.sale.model.BrandData;
import com.akshit.sale.model.BrandForm;
import com.akshit.sale.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public BrandData select(@PathVariable int id) {
		return dto.select(id);
	}

	@ApiOperation(value = "Gets list of all brands")
	@RequestMapping(method = RequestMethod.GET)
	public List<BrandData> getAll() {
		return dto.getAll();
	}

}

