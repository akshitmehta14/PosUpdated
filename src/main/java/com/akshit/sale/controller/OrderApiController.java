package com.akshit.sale.controller;

import com.akshit.sale.dto.OrderDto;
import com.akshit.sale.model.OrderData;
import com.akshit.sale.model.OrderForm;
import com.akshit.sale.model.OrderHistoryData;
import com.akshit.sale.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api
@RestController
@RequestMapping(path = "/api/order")
public class OrderApiController {
	@Autowired
	private OrderDto orderDto;


	@ApiOperation(value = "Shows all order")
	@RequestMapping(method = RequestMethod.GET)
	public List<OrderHistoryData> getAll() {
		return orderDto.getAll();
	}

	@ApiOperation(value = "Shows details about an order")
	@RequestMapping(path = "/{id}",method = RequestMethod.GET)
	public List<OrderData> select(@PathVariable int id) throws ApiException{
		return orderDto.select(id);
	}
	@ApiOperation(value="Creates an order")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody List<OrderForm> f) throws ApiException {
		orderDto.add(f);
	}
}
