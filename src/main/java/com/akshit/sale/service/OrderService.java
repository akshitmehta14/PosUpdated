package com.akshit.sale.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshit.sale.dao.InventoryDao;
import com.akshit.sale.dao.OrderDao;
import com.akshit.sale.dao.OrderHistoryDao;
import com.akshit.sale.dao.ProductDao;
import com.akshit.sale.model.OrderForm;
import com.akshit.sale.pojo.Inventory;
import com.akshit.sale.pojo.OrderHistory;
import com.akshit.sale.pojo.OrderItem;
import com.akshit.sale.pojo.Product;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {
	@Autowired 
	private OrderHistoryDao orderhistory; 
	@Autowired 
	private OrderDao orderdao;
	@Autowired
	private InventoryDao inventorydao;
	@Autowired
	private ProductDao productdao;

//	public void add(List<OrderForm> f) throws ApiException {
//		//List<OrderItem> ans = check(f);
//		OrderHistory x = convert();
//		orderhistory.add(x);
//		for(OrderItem r:ans) {
//
//			orderdao.add(r);
//			//InventoryDao v = new InventoryDao();
//			//v.updateinventory(r.getProduct_id(),r.getQuantity());
//		}
//	}
	public void add(List<OrderForm> f) throws ApiException {
		List<OrderItem> ans = check(f);
		OrderHistory x = convert();
		int a = orderhistory.add(x);
		for(OrderItem r:ans) {
			r.setOrder_id(a);
//			i.setQuantity(r.getQuantity());
//			i.setProduct_id(4);
			orderdao.add(r);
			inventorydao.updateinventory(r.getProduct_id(),r.getQuantity());
		}
	}

	public List<OrderHistory> getall() {
		return orderhistory.getall();
	}
	
	private List<OrderItem> check(List<OrderForm> f) throws ApiException {
		List<OrderItem> ans = new ArrayList<OrderItem>();
		for(OrderForm p:f) {
			OrderItem q = new OrderItem();
			Product z = productdao.select(p.getBarcode());
			if(z==null) {
				throw new ApiException("No such product exists.");
			}
			q.setProduct_id(z.getProduct_id());
			Inventory i = inventorydao.select(z.getProduct_id());
			q.setQuantity(p.getQuantity());
			if(i==null) {
				throw new ApiException("No inventory exists for this product.");
			}
			if(p.getQuantity() > i.getQuantity()) {
				throw new ApiException("Selected quantity cannot be greater than Inventory");
			}q.setOrder_id(1);
			ans.add(q);
		}
		return ans;
	}
	private OrderHistory convert() {
		OrderHistory h = new OrderHistory();
		LocalDateTime now = LocalDateTime.now();
		h.setDate(now);
		return h;
	}

}
