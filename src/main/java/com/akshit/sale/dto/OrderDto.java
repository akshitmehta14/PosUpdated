package com.akshit.sale.dto;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.akshit.sale.model.OrderData;
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

	public List<OrderData> select(int id) throws ApiException{
		return orderservice.select(id);
	}

	@Transactional
	public void add(List<OrderForm> form) throws ApiException {
		for(OrderForm f:form ){
			if(StringUtil.isEmpty(f.getBarcode())){
				throw new ApiException("Barcode cannot be empty");
			}
			if(StringUtil.negative(f.getQuantity())){
				throw new ApiException("Quantity cannot be negative");
			}
		}
		orderservice.add(form);
	}
}
