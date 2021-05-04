package com.akshit.sale.controller;

import java.io.IOException;
import java.util.List;

import com.akshit.sale.model.InvoiceModel;
import com.akshit.sale.model.OrderData;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.OrderService;
import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import com.akshit.sale.dto.OrderDto;
import com.akshit.sale.model.OrderForm;
import com.akshit.sale.pojo.OrderHistory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
public class OrderApiController {
	@Autowired
	private OrderDto orderdto;
	@Autowired
	private OrderService orderservice;


	@ApiOperation(value = "Shows all order")
	@RequestMapping(path = "/api/order", method = RequestMethod.GET)
	public List<OrderHistory> getAll() {
		return orderdto.getall();
	}

	@ApiOperation(value = "Shows details about an order")
	@RequestMapping(path = "/api/order/{id}",method = RequestMethod.GET)
	public List<OrderData> select(@PathVariable int id) throws ApiException{
		return orderdto.select(id);
	}
	@ApiOperation(value="Creates an order")
	@RequestMapping(path = "/api/order", method = RequestMethod.POST)
	public void add(@RequestBody List<OrderForm> f,HttpServletResponse response) throws FOPException, JAXBException, IOException, TransformerException, ApiException {
		orderdto.add(f, response);

	}
}
