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
	private static String selectbyid = "select p from BrandDetail p where p.brand_id = :id";
	private static String select_all = "select p from BrandDetail p";
	private static String updatebyid = "update BrandDetail set brand=:brand , category=:category where brand_id=:id";
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insert(BrandDetail p) {
		em.persist(p);
	}
	@Transactional
	public void update(int id, BrandForm f){
		em.createQuery(updatebyid).setParameter("id",id).setParameter("brand",f.getBrand()).setParameter("category",f.getCategory()).executeUpdate();

	}
	@Transactional
	public BrandDetail select(int id){
		TypedQuery<BrandDetail> query = getQuery(selectbyid,BrandDetail.class);
		query.setParameter("id",id);
		return getSingle(query);
	}
	 public BrandDetail select(BrandForm f) { 
		 TypedQuery<BrandDetail> query = getQuery(select_id, BrandDetail.class);
		 query.setParameter("brand", f.getBrand()).setParameter("category",f.getCategory());
		 return getSingle(query);
	}

	public List<BrandDetail> selectAll() {
		TypedQuery<BrandDetail> query = getQuery(select_all, BrandDetail.class);
		return query.getResultList();
	}

}
