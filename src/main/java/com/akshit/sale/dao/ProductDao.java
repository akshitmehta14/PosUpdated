package com.akshit.sale.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.akshit.sale.pojo.Product;

@Repository
public class ProductDao extends AbstractDao {
	
	private static String select_all = "select p from Product p";
	
	@PersistenceContext
	private EntityManager em;

	
	public List<Product> getall() {
		TypedQuery<Product> query = getQuery(select_all, Product.class);
		return query.getResultList();
	}
	@Transactional
	public void add(Product x) {
		em.persist(x);
	}

}
