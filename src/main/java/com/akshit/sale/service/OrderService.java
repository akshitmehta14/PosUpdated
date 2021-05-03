package com.akshit.sale.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.akshit.sale.model.InvoiceModel;
import com.akshit.sale.model.OrderData;
import com.akshit.sale.model.OrderInvoiceXmlList;
import com.akshit.sale.util.PDFConversion;
import org.apache.fop.apps.FOPException;
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

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

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
	@Transactional(rollbackOn  = ApiException.class)
	public void add(List<OrderForm> f,HttpServletResponse response) throws IOException, FOPException, JAXBException, TransformerException, ApiException {
		List<OrderItem> ans = check(f);
		OrderHistory x = convert();
		List<InvoiceModel> invoice = new ArrayList<InvoiceModel>();
		int a = orderhistory.add(x);
		for(OrderItem r:ans) {

			r.setOrder_id(a);
//			i.setQuantity(r.getQuantity());
//			i.setProduct_id(4);
			orderdao.add(r);
		}


	}

	public List<OrderHistory> getall() {
		return orderhistory.getall();
	}
	public List<OrderData> select(int id) throws ApiException{
		List<OrderItem> items = orderdao.select(id);
		if(items==null){
			throw new ApiException("Order does not exist");
		}
		return convert(items);
	}
	@Transactional(rollbackOn  = ApiException.class)
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
			q.setMrp(z.getMrp());
			inventorydao.updateinventory(i.getProduct_id(),p.getQuantity());
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
	protected List<OrderData> convert(List<OrderItem> items){
		List<OrderData> ans = new ArrayList<OrderData>();
		for(OrderItem i:items){
			OrderData r = new OrderData();
			r.setMrp(i.getMrp());
			r.setQuantity(i.getQuantity());
			r.setName(productdao.select(i.getProduct_id()).getName());
			ans.add(r);
		}
		return ans;
	}

}
