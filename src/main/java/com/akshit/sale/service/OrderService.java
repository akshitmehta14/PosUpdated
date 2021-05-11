package com.akshit.sale.service;

import com.akshit.sale.dao.InventoryDao;
import com.akshit.sale.dao.OrderHistoryDao;
import com.akshit.sale.dao.OrderItemDao;
import com.akshit.sale.dao.ProductDao;
import com.akshit.sale.model.OrderData;
import com.akshit.sale.model.OrderForm;
import com.akshit.sale.pojo.Inventory;
import com.akshit.sale.pojo.OrderHistory;
import com.akshit.sale.pojo.OrderItem;
import com.akshit.sale.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderHistoryDao orderHistoryDao;
    @Autowired
    private OrderItemDao orderDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ProductDao productDao;


    @Transactional
    public void add(List<OrderForm> f) throws ApiException {
        List<OrderItem> ans = check(f);
        OrderHistory x = convert();
        int a = orderHistoryDao.add(x);
        for (OrderItem r : ans) {

            r.setOrder_id(a);
            orderDao.add(r);
        }
    }

    public List<OrderHistory> getAll() {
        return orderHistoryDao.getall();
    }

    public List<OrderData> select(int id) throws ApiException {
        List<OrderItem> items = orderDao.select(id);
        if (items == null) {
            throw new ApiException("Order does not exist");
        }
        return convert(items);
    }

    @Transactional(rollbackOn = ApiException.class)
    private List<OrderItem> check(List<OrderForm> f) throws ApiException {
        List<OrderItem> ans = new ArrayList<OrderItem>();
        for (OrderForm p : f) {
            OrderItem q = new OrderItem();
            Product z = productDao.select(p.getBarcode());
            if (z == null) {
                throw new ApiException("No such product exists.");
            }
            q.setProduct_id(z.getProduct_id());
            Inventory i = inventoryDao.select(z.getProduct_id());
            q.setQuantity(p.getQuantity());
            if (i == null) {
                throw new ApiException("No inventory exists for this product.");
            }
            if (p.getQuantity() > i.getQuantity()) {
                throw new ApiException("Selected quantity cannot be greater than Inventory");
            }
            q.setOrder_id(1);
            q.setMrp(z.getMrp());
            inventoryDao.updateinventory(i.getProduct_id(), p.getQuantity());
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

    private List<OrderData> convert(List<OrderItem> items) {
        List<OrderData> ans = new ArrayList<OrderData>();
        for (OrderItem i : items) {
            OrderData r = new OrderData();
            r.setMrp(i.getMrp());
            r.setQuantity(i.getQuantity());
            r.setName(productDao.select(i.getProduct_id()).getName());
            ans.add(r);
        }
        return ans;
    }

}
