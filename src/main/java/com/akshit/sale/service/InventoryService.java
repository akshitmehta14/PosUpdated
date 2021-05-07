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
	public void update(String barcode,int quantity) throws ApiException {
		int id = barcodetoid(barcode);
		if(quantity<0){
			throw new ApiException("Quantity cannot be negative");
		}
		dao.setInventory(id,quantity);
	}
	public void add(Inventory i,String barcode) throws ApiException {
		Product p = x.select(barcode);
		if(p==null) {
			throw new ApiException("No such product exists.");
		}
		Inventory inv = dao.select(p.getProduct_id());
		if(inv!=null){
			if(inv.getQuantity()+ i.getQuantity()<0){
				throw new ApiException("Quantity cannot be negative");
			}
			dao.update(p.getProduct_id(),i.getQuantity());
			return;
		}
		i.setProduct_id(p.getProduct_id());
		dao.add(i);
	}
	public InventoryData select(String barcode){
		int id = barcodetoid(barcode);
		Inventory i = dao.select(id);
		Product p = x.select(id);
		return convert(p,i);
	}
	private int barcodetoid(String barcode){
		return x.select(barcode).getProduct_id();
	}
	private List<InventoryData> convert(List<Product> list1, List<Inventory> list2){
		List<InventoryData> ans = new ArrayList<InventoryData>();
		for(Inventory i:list2) {
			for(Product p:list1) {
				if(i.getProduct_id() == p.getProduct_id()) {
					InventoryData y= convert(p,i);
					ans.add(y);
				}
			}
		}
		return ans;
	}
	protected InventoryData convert(Product p,Inventory i){
		InventoryData y= new InventoryData();
		y.setProduct_id(i.getProduct_id());
		y.setBarcode(p.getBarcode());
		y.setName(p.getName());
		y.setQuantity(i.getQuantity());
		return y;
	}
}
