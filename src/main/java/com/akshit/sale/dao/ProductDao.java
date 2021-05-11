package com.akshit.sale.dao;

import com.akshit.sale.pojo.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao {
	
	private static String SELECT_ALL = "select p from Product p";
	private static String SELECT_ID = "select p from Product p where barcode=:barcode";
	private static String UPDATE_BY_ID ="update Product set brand_id = :brand_id, name=:name , mrp=:mrp,barcode =:barcode,name=:name  where id = :id";

	
	public List<Product> getall() {
		TypedQuery<Product> query = getQuery(SELECT_ALL, Product.class);
		return query.getResultList();
	}
	@Transactional
	public void update(int id,Product p){

		em().createQuery(UPDATE_BY_ID).setParameter("id", id)
				.setParameter("brand_id",p.getBrand_id())
				.setParameter("name", p.getName())
				.setParameter("mrp", p.getMrp())
				.setParameter("barcode", p.getBarcode())
				.executeUpdate();
	}
	@Transactional
	public void add(Product x) {
		em().persist(x);
	}
	public Product select(String barcode) { 
		 TypedQuery<Product> query = getQuery(SELECT_ID, Product.class);
		 query.setParameter("barcode", barcode); 
		 return getSingle(query); 
	}
	public Product select(int id) {
		TypedQuery<Product> query = getQuery("select p from Product p where product_id=:product_id", Product.class);
		query.setParameter("product_id", id);
		return getSingle(query);
	}
}
