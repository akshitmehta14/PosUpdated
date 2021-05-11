package com.akshit.sale.service;

import com.akshit.sale.dao.ProductDao;
import com.akshit.sale.model.ProductData;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductDao productDao;

    public void add(Product product) throws ApiException {
        if (productDao.select(product.getBarcode()) != null) {
            throw new ApiException("Product already exists.");
        }
        productDao.add(product);
    }

    public List<ProductData> getAll() {
        return innerjoin(productDao.getall(), brandService.getAll());
    }

    public void update(int id, Product p) throws ApiException {
        productDao.update(id, p);
    }

    public ProductData select(int id) throws ApiException {
        Product p = productDao.select(id);
        if (p == null) {
            throw new ApiException("No such product exists.");
        }
        BrandDetail b = brandService.select(p.getBrand_id());
        return convert(p, b);
    }

    public ProductData select(String barcode) throws ApiException {
        Product p = productDao.select(barcode);
        if (p == null) {
            throw new ApiException("No such product exists.");
        }
        BrandDetail b = brandService.select(p.getBrand_id());
        return convert(p, b);
    }

    private ProductData convert(Product f, BrandDetail p) {
        ProductData x = new ProductData();
        x.setProduct_id(f.getProduct_id());
        x.setBarcode(f.getBarcode());
        x.setBrand(p.getBrand());
        x.setCategory(p.getCategory());
        x.setMrp(f.getMrp());
        x.setName(f.getName());
        return x;
    }

    private List<ProductData> innerjoin(List<Product> list1, List<BrandDetail> list2) {
        List<ProductData> list3 = new ArrayList<ProductData>();
        HashMap<Integer,BrandDetail> hm =new HashMap<Integer, BrandDetail>();
        for(BrandDetail b:list2){
            hm.put(b.getBrand_id(),b);
        }
        for (Product p : list1) {
            BrandDetail b = hm.get(p.getBrand_id());
            list3.add(convert(p, b));
        }
        return list3;
    }

}
