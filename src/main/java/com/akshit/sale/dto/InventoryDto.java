package com.akshit.sale.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import com.akshit.sale.model.InventoryData;
import com.akshit.sale.model.InventoryForm;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.InventoryService;
@Service
@Transactional
public class InventoryDto {
	@Autowired
	private InventoryService service;
	public void add(InventoryForm form) throws ApiException {
		normalize(form);
		service.add(form);
	}
	
	
	public List<InventoryData> getall() {
		return service.getall();
	}
	
	private void normalize(InventoryForm p) throws ApiException{
		if(StringUtil.negative(p.getQuantity())==true) {
			throw new ApiException("quantity cannot be negative");
		}
		
		if(StringUtil.isEmpty(p.getBarcode())==true) {
			throw new ApiException("barcode cannot be empty");
		}
		StringUtil.toLowerCase(p.getBarcode());
		return;
	}
}
