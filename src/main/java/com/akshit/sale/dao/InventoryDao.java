package com.akshit.sale.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.akshit.sale.pojo.Inventory;

@Repository
@Transactional
public class InventoryDao extends AbstractDao {
	
	private static String select_all = "select p from Inventory p";
	private static String select_id = "select p from Inventory p where product_id=:id";
	private static String update = "update p from Inventory p set quantity=:quantity where product_id=:id";
	@PersistenceContext
	private EntityManager em;

	
	public List<Inventory> getall() {
		TypedQuery<Inventory> query = getQuery(select_all, Inventory.class);
		return query.getResultList();
	}
	
	public void add(Inventory x) {
		em.persist(x);
	}
	public Inventory select(int Id) { 
		 TypedQuery<Inventory> query = getQuery(select_id, Inventory.class); 
		 query.setParameter("id", Id); 
		 return getSingle(query); 
	}
	public void updateinventory(int id,int quantity){
		TypedQuery<Inventory> query = em.createQuery(update,Inventory.class);
		query.setParameter("quantity",quantity).setParameter("id",id);
		query.executeUpdate();
	}
}