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
		if(productdao.select(form.getBarcode())!=null){
			throw new ApiException("Product already exists.");
		}
		if(p==null) {
			throw new ApiException("Brand and Category combination does not exists.");
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

    public void update(int id, ProductForm f) throws ApiException {
		Product p = new Product();
		BrandForm x = new BrandForm();
		x.setBrand(f.getBrand());
		x.setCategory(f.getCategory());
		BrandDetail b = branddao.select(x);
		if(b==null){
			throw new ApiException("Failed to update as brand and category does not exists");
		}
		p.setProduct_id(id);
		p.setBrand_id(b.getBrand_id());
		p.setMrp(f.getMrp());
		p.setBarcode(f.getBarcode());
		p.setName(f.getName());
		productdao.update(p);
    }
}
