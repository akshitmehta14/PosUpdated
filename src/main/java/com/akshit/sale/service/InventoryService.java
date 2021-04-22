package com.akshit.sale.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshit.sale.dao.InventoryDao;
import com.akshit.sale.dao.ProductDao;
import com.akshit.sale.model.InventoryData;
import com.akshit.sale.model.InventoryForm;
import com.akshit.sale.pojo.Inventory;
import com.akshit.sale.pojo.Product;



@Service
@Transactional
public class InventoryService {
	@Autowired
	private InventoryDao dao;
	@Autowired
	private ProductDao x;
	
	public List<InventoryData> getall() {
		List<InventoryData> i = convert(x.getall(),dao.getall());
		return i;
	}

	public void add(InventoryForm form) throws ApiException {
		Product p = x.select(form.getBarcode());
		if(p==null) {
			throw new ApiException("No such product exists.");
		}
		Inventory y = new Inventory();
		y.setProduct_id(p.getProduct_id());
		y.setQuantity(form.getQuantity());
		dao.add(y);
	}
	private List<InventoryData> convert(List<Product> list1, List<Inventory> list2){
		List<InventoryData> ans = new ArrayList<InventoryData>();
		for(Inventory i:list2) {
			for(Product p:list1) {
				if(i.getProduct_id() == p.getProduct_id()) {
					InventoryData y= new InventoryData();
					y.setBarcode(p.getBarcode());
					y.setName(p.getName());
					y.setQuantity(i.getQuantity());
					ans.add(y);
				}
			}
		}
		return ans;
	}

}
