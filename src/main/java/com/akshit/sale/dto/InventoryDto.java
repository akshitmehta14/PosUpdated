package com.akshit.sale.dto;

import java.util.List;

import com.akshit.sale.model.ProductData;
import com.akshit.sale.pojo.Inventory;
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
		form = normalize(form);
		Inventory i = convert(form);
		service.add(i,form.getBarcode());
	}
	public void update(String barcode, int quantity) throws ApiException {
		barcode = StringUtil.toLowerCase(barcode);
		service.update(barcode,quantity);
	}
	public InventoryData select(String barcode) throws ApiException {
		barcode = StringUtil.toLowerCase(barcode);
		if(StringUtil.isEmpty(barcode)){
			throw new ApiException("Barcode cannot be empty");
		}
		return service.select(barcode);
	}
	public List<InventoryData> getall() {
		return service.getall();
	}
	
	private InventoryForm normalize(InventoryForm p) throws ApiException{
		if(StringUtil.negative(p.getQuantity())==true) {
			throw new ApiException("quantity cannot be negative");
		}
		
		if(StringUtil.isEmpty(p.getBarcode())==true) {
			throw new ApiException("barcode cannot be empty");
		}
		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
		return p;
	}
	protected Inventory convert(InventoryForm form){
		Inventory converted = new Inventory();
		converted.setQuantity(form.getQuantity());
		return converted;
	}
}
