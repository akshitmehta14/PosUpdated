package com.akshit.sale.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.akshit.sale.dto.InventoryDto;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.model.InventoryData;
import com.akshit.sale.model.InventoryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController

public class InventoryApiController {
	@Autowired
	private InventoryDto inventorydto;
	
	@ApiOperation(value = "Adds into inventory")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
	public void add(@RequestBody InventoryForm form) throws ApiException {
		inventorydto.add(form);
	}
	@ApiOperation(value = "Shows all inventory")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
	public List<InventoryData> add() {
		return inventorydto.getall();
	}
}