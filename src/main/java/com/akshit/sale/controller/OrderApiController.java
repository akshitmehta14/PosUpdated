package com.akshit.sale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.akshit.sale.dto.OrderDto;
import com.akshit.sale.model.OrderForm;
import com.akshit.sale.pojo.OrderHistory;
import com.akshit.sale.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
public class OrderApiController {
	@Autowired
	private OrderDto orderdto;
	@ApiOperation(value = "Shows all order")
	@RequestMapping(path = "/api/order", method = RequestMethod.GET)
	public List<OrderHistory> add() {
		return orderdto.getall();
	}
	@ApiOperation(value="Creates an order")
	@RequestMapping(path = "/api/order", method = RequestMethod.POST)
	public void add(@RequestBody List<OrderForm> f) throws ApiException {
		orderdto.add(f);
	}
}
