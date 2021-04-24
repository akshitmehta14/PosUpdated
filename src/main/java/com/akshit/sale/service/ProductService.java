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
	public void add(ProductForm form) throws ApiException {
		BrandForm f = new BrandForm();
		f.setBrand(form.getBrand());
		f.setCategory(form.getCategory());
		BrandDetail p = branddao.select(f);
		if(p==null) {
			throw new ApiException("Brand and Category combination does not exists");
		}
		Product x = convert(form,p);
		productdao.add(x);
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
	private ProductData convert(Product f, BrandDetail p) {
		ProductData x = new ProductData();
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

    public void update(int id, ProductForm f) {
		Product p = new Product();
		BrandForm x = new BrandForm();
		x.setBrand(f.getBrand());
		x.setCategory(f.getCategory());
		BrandDetail b = branddao.select(x);
		p.setProduct_id(id);
		p.setBrand_id(b.getBrand_id());
		p.setMrp(f.getMrp());
		p.setBarcode(f.getBarcode());
		p.setName(f.getName());
		productdao.update(p);
    }
}
