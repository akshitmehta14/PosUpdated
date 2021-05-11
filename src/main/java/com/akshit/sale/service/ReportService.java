package com.akshit.sale.service;

import com.akshit.sale.dao.BrandDao;
import com.akshit.sale.dao.OrderItemDao;
import com.akshit.sale.dao.OrderHistoryDao;
import com.akshit.sale.dao.ProductDao;
import com.akshit.sale.model.FilterForm;
import com.akshit.sale.model.SalesData;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.pojo.OrderHistory;
import com.akshit.sale.pojo.OrderItem;
import com.akshit.sale.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private OrderItemDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private OrderHistoryDao historyDao;

    public List<SalesData> generateSalesReport(FilterForm form){
        List<OrderItem> item = orderDao.selectAll();
        List<OrderItem> items = filtering(item,form);
        List<SalesData> s = convert(items);
        List<SalesData> g = grouping(s);
        return g;
    }
    public List<OrderItem> filtering(List<OrderItem> list, FilterForm filter){
        List<OrderItem> list2 = new ArrayList<OrderItem>();
        for(OrderItem r:list) {
            list2.add(r);
            BrandDetail find = brandDao.select(productDao.select(r.getProduct_id()).getBrand_id());
            if (filter.getCategory()!= null && !filter.getCategory().equals(find.getCategory()) && filter.getCategory()!="") {
                list2.remove(r);
                continue;
            }
            else if(filter.getBrand()!=null && !filter.getBrand().equals(find.getBrand()) && filter.getBrand()!=""){
                list2.remove(r);
                continue;
            }
            OrderHistory his = historyDao.select(r.getOrder_id());
            if(filter.getStart().isAfter(his.getDate()) || filter.getEnd().isBefore(his.getDate())){
                list2.remove(r);
            }
        }

        return list2;
    }
    public List<SalesData> grouping(List<SalesData> s){
        HashMap<String, SalesData> hm = new HashMap<String,SalesData>();
        for(SalesData r:s){
            if(hm.containsKey(r.getCategory()) == false){
                hm.put(r.getCategory(),r);
            }
            else {
                SalesData temp = hm.get(r.getCategory());
                temp.setQuantity(temp.getQuantity() + r.getQuantity());
                temp.setRevenue(temp.getRevenue() + r.getRevenue());
                hm.put(r.getCategory(), temp);
            }
        }
        List<SalesData> afterGrouping = new ArrayList<SalesData>();
        for (Map.Entry<String,SalesData> e : hm.entrySet()){
            afterGrouping.add(e.getValue());
        }
        return afterGrouping;
    }
    protected List<SalesData> convert(List<OrderItem> item){
        List<SalesData> s = new ArrayList<SalesData>();
        for(OrderItem i:item){
            SalesData n = new SalesData();
            n.setQuantity(i.getQuantity());
            Product p = productDao.select(i.getProduct_id());
            BrandDetail b = brandDao.select(p.getBrand_id());
            n.setCategory(b.getCategory());
            n.setRevenue(i.getQuantity()*i.getMrp());
            s.add(n);
        }
        return s;
    }

}
