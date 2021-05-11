package com.akshit.sale.dto;


import com.akshit.sale.service.OrderService;
import com.akshit.sale.model.OrderData;
import com.akshit.sale.model.OrderForm;
import com.akshit.sale.model.OrderHistoryData;
import com.akshit.sale.pojo.OrderHistory;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDto {
	@Autowired
	private OrderService orderService;
	
	public List<OrderHistoryData> getAll() {
		return convert(orderService.getAll());
	}

	public List<OrderData> select(int id) throws ApiException{
		return orderService.select(id);
	}

	public void add(List<OrderForm> form) throws ApiException {
		for(OrderForm f:form ){
			if(StringUtil.isEmpty(f.getBarcode())){
				throw new ApiException("Barcode cannot be empty");
			}
			if(StringUtil.negative(f.getQuantity())){
				throw new ApiException("Quantity cannot be negative");
			}
		}
		orderService.add(form);
	}
	private List<OrderHistoryData> convert(List<OrderHistory> list){
		List<OrderHistoryData> data = new ArrayList<OrderHistoryData>();
		for(OrderHistory temp:list) {
			OrderHistoryData obj = new OrderHistoryData();
			obj.setOrder_id(temp.getOrder_id());
			obj.setDate(temp.getDate());
			data.add(obj);
		}
		return data;
	}
}
