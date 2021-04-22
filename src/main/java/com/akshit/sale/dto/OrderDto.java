package com.akshit.sale.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshit.sale.model.OrderForm;
import com.akshit.sale.pojo.OrderHistory;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.OrderService;

@Service
public class OrderDto {
	@Autowired
	private OrderService orderservice;
	
	public List<OrderHistory> getall() {
		return orderservice.getall();
	}
	
	@Transactional
	public void add(List<OrderForm> f) throws ApiException {
		orderservice.add(f);
	}
}
