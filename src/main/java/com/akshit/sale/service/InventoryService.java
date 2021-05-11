package com.akshit.sale.service;

import com.akshit.sale.dao.InventoryDao;
import com.akshit.sale.dao.ProductDao;
import com.akshit.sale.model.InventoryData;
import com.akshit.sale.pojo.Inventory;
import com.akshit.sale.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class InventoryService {
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ProductDao productDao;

    @Transactional(readOnly = true)
    public List<InventoryData> getAll() {
        List<Product> list1 = productDao.getall();
        List<Inventory> list2 = inventoryDao.getall();
        List<InventoryData> ans = convert(list1, list2);
        return ans;
    }

    @Transactional
    public void update(String barcode, int quantity) throws ApiException {
        int id = barcodeToId(barcode);
        if (quantity < 0) {
            throw new ApiException("Quantity cannot be negative");
        }
        inventoryDao.setInventory(id, quantity);
    }

    public void add(Inventory inventory, String barcode) throws ApiException {
        Product p = productDao.select(barcode);
        if (p == null) {
            throw new ApiException("No such product exists.");
        }
        Inventory inv = inventoryDao.select(p.getProduct_id());
        if (inv != null) {
            if (inv.getQuantity() + inventory.getQuantity() < 0) {
                throw new ApiException("Quantity cannot be negative");
            }
            inventoryDao.update(p.getProduct_id(), inventory.getQuantity());
            return;
        }
        if(inventory.getQuantity()<0){
            throw new ApiException("Quantity cannot be negative");
        }
        inventory.setProduct_id(p.getProduct_id());
        inventoryDao.add(inventory);
    }

    @Transactional(readOnly = true)
    public InventoryData select(String barcode) throws ApiException {
        int id = barcodeToId(barcode);
        Inventory i = inventoryDao.select(id);
        Product p = productDao.select(id);
        if(p==null){
            throw new ApiException("No such product exists");
        }
        if(i==null){
            throw new ApiException("No inventory for product exists");
        }
        return convert(p, i);
    }

    @Transactional(readOnly = true)
    public InventoryData select(int id) throws ApiException {
        Inventory i = inventoryDao.select(id);
        Product p = productDao.select(id);
        if(p==null){
            throw new ApiException("No such product exists");
        }
        if(i==null){
            throw new ApiException("No inventory for product exists");
        }
        return convert(p, i);
    }

    private int barcodeToId(String barcode) {
        return productDao.select(barcode).getProduct_id();
    }

    private List<InventoryData> convert(List<Product> list1, List<Inventory> list2) {
        List<InventoryData> ans = new ArrayList<InventoryData>();
        HashMap<Integer,Product> hm = new HashMap<Integer,Product>();
        for (Product p : list1) {
            hm.put(p.getProduct_id(),p);
        }
        for (Inventory i : list2) {
            Product p = hm.get(i.getProduct_id());
            ans.add(convert(p, i));
        }
        return ans;
    }

    private InventoryData convert(Product p, Inventory i) {
        InventoryData y = new InventoryData();
        y.setProduct_id(i.getProduct_id());
        y.setBarcode(p.getBarcode());
        y.setName(p.getName());
        y.setQuantity(i.getQuantity());
        return y;
    }
}
