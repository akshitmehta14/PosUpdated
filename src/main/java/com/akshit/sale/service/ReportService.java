package com.akshit.sale.service;

import com.akshit.sale.dao.BrandDao;
import com.akshit.sale.dao.OrderDao;
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
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    private OrderDao orderdao;
    @Autowired
    private ProductDao productdao;
    @Autowired
    private BrandDao branddao;
    @Autowired
    private OrderHistoryDao historyDao;

    public List<SalesData> salesgeneration(FilterForm form){
        List<OrderItem> item = orderdao.selectAll();
        List<OrderItem> items = filtering(item,form);
        List<SalesData> s = convert(items);
        List<SalesData> g = grouping(s);
        return g;
    }
    public List<OrderItem> filtering(List<OrderItem> list, FilterForm filter){
//        list.stream().filter(orderItem -> orderItem.getItem()==1).collect(Collectors.toList());
        List<OrderItem> list2 = new ArrayList<OrderItem>();
        for(OrderItem r:list) {
            list2.add(r);
            BrandDetail find = branddao.select(productdao.select(r.getProduct_id()).getBrand_id());
            if (filter.getCategory()!= null && !filter.getCategory().equals(find.getCategory()) && filter.getCategory()!="") {
                list2.remove(r);
                continue;
            }
            else if(filter.getBrand()!=null && filter.getBrand()!=find.getBrand() && filter.getBrand()!=""){
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
        List<SalesData> ans = new ArrayList<SalesData>();
        for (Map.Entry<String,SalesData> e : hm.entrySet()){
            ans.add(e.getValue());
        }
        return ans;
    }
    protected List<SalesData> convert(List<OrderItem> item){
        List<SalesData> s = new ArrayList<SalesData>();
        for(OrderItem i:item){
            SalesData n = new SalesData();
            n.setQuantity(i.getQuantity());
            Product p = productdao.select(i.getProduct_id());
            BrandDetail b = branddao.select(p.getBrand_id());
            n.setCategory(b.getCategory());
            n.setRevenue(i.getQuantity()*i.getMrp());
            s.add(n);
        }
        return s;
    }

}
