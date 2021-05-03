package com.akshit.sale.dto;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import com.akshit.sale.model.OrderData;
import org.apache.fop.apps.FOPException;
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
	public void add(List<OrderForm> form, HttpServletResponse response) throws ApiException, FOPException, JAXBException, IOException, TransformerException {
		for(OrderForm f:form ){
			if(StringUtil.isEmpty(f.getBarcode())){
				throw new ApiException("Barcode cannot be empty");
			}
			if(StringUtil.negative(f.getQuantity())){
				throw new ApiException("Quantity cannot be negative");
			}
		}
		orderservice.add(form,response);
	}
}
