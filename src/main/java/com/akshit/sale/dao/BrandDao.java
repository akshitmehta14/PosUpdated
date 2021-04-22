package com.akshit.sale.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import com.akshit.sale.model.BrandForm;
import com.akshit.sale.pojo.BrandDetail;

@Repository
public class BrandDao extends AbstractDao{

	//private static String delete_id = "delete from EmployeePojo p where id=:id";
	private static String select_id = "select p from BrandDetail p where p.brand=:brand AND p.category=:category";
	private static String select_all = "select p from BrandDetail p";

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insert(BrandDetail p) {
		em.persist(p);
	}

	/*
	 * public int delete(int id) { Query query = em.createQuery(delete_id);
	 * query.setParameter("id", id); return query.executeUpdate(); }
	 */

	//Forms and data classes should not go beyond dto/service layer
	 public BrandDetail select(BrandForm f) { 
		 TypedQuery<BrandDetail> query = getQuery(select_id, BrandDetail.class);
		 query.setParameter("brand", f.getBrand()).setParameter("category",f.getCategory());
		 return getSingle(query); 
		 }
	 

	public List<BrandDetail> selectAll() {
		TypedQuery<BrandDetail> query = getQuery(select_all, BrandDetail.class);
		return query.getResultList();
	}

	/*
	 * public void update(EmployeePojo p) { }
	 */



}
