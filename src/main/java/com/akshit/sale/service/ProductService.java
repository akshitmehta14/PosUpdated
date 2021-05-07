package com.akshit.sale.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshit.sale.dao.BrandDao;
import com.akshit.sale.dao.ProductDao;
import com.akshit.sale.model.BrandForm;
import com.akshit.sale.model.ProductForm;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.pojo.Product;

import com.akshit.sale.model.ProductData;
@Service
public class ProductService {
	@Autowired
	private BrandDao branddao;
	
	@Autowired
	private ProductDao productdao;
	public void add(Product p) throws ApiException {
		if(productdao.select(p.getBarcode())!=null){
			throw new ApiException("Product already exists.");
		}
		productdao.add(p);
	}
	public List<ProductData> getall() {
		return innerjoin(productdao.getall(),branddao.selectAll());
		
	}
	private Product convert(ProductForm f, BrandDetail p) {
		Product x = new Product();
		x.setBarcode(f.getBarcode());
		x.setBrand_id(p.getBrand_id());
		x.setMrp(f.getMrp());
		x.setName(f.getName());
		return x;
	}
	public ProductData select(int id){
		Product p = productdao.select(id);
		BrandDetail b = branddao.select(p.getBrand_id());
		return convert(p,b);
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
	private List<ProductData> innerjoin(List<Product> list1, List<BrandDetail> list2){
		List<ProductData> list3 = new ArrayList<ProductData>(); 
		for(Product p:list1) {
			for(BrandDetail b:list2) {
				if(p.getBrand_id() == b.getBrand_id()) {
					list3.add(convert(p,b));
				}
			}
		}
		return list3;
	}

    public void update(int id, Product p) throws ApiException {
		productdao.update(id,p);
    }
}
