package com.akshit.sale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.akshit.sale.dto.InventoryDto;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.model.InventoryData;
import com.akshit.sale.model.InventoryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(path = "api/inventory")
public class InventoryApiController {
	@Autowired
	private InventoryDto inventorydto;
	
	@ApiOperation(value = "Adds into inventory")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody InventoryForm form) throws ApiException {
		inventorydto.add(form);
	}
	@ApiOperation(value = "Updates a product")
	@RequestMapping(path = "/{barcode}",method = RequestMethod.PUT)
	public void update(@PathVariable String barcode,@RequestBody InventoryForm form) throws ApiException {
		inventorydto.update(barcode, form.getQuantity());
	}
	@ApiOperation(value = "Selects a product")
	@RequestMapping(path = "/{barcode}",method = RequestMethod.GET)
	public InventoryData select(@PathVariable String barcode) throws ApiException {
		return inventorydto.select(barcode);
	}
	@ApiOperation(value = "Shows all inventory")
	@RequestMapping(method = RequestMethod.GET)
	public List<InventoryData> getAll() {
		return inventorydto.getall();
	}
}