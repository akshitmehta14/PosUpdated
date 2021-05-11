package com.akshit.sale.dao;

import com.akshit.sale.pojo.Inventory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class InventoryDao extends AbstractDao {
	
	private static String SELECT_ALL = "select p from Inventory p";
	private static String SELECT_ID = "select p from Inventory p where product_id=:id";
	private static String UPDATE_BY_ID = "update Inventory set quantity = quantity - :quantity where product_id=:id";
	private static String UPDATE_INV = "update Inventory set quantity = quantity + :quantity where product_id=:id";
	private static String SET_INVENTORY = "update Inventory set quantity = :quantity where product_id=:id";
	
	public List<Inventory> getall() {
		TypedQuery<Inventory> query = getQuery(SELECT_ALL, Inventory.class);
		return query.getResultList();
	}
	
	public void add(Inventory x) {
		em().persist(x);
	}
	public Inventory select(int Id) { 
		 TypedQuery<Inventory> query = getQuery(SELECT_ID, Inventory.class);
		 query.setParameter("id", Id); 
		 return getSingle(query); 
	}
	public void update(int id,int quantity){
		em().createQuery(UPDATE_INV).
				setParameter("quantity",quantity).
				setParameter("id",id).
				executeUpdate();
	}
	public void updateinventory(int id,int quantity){
		em().createQuery(UPDATE_BY_ID).
				setParameter("quantity",quantity).
				setParameter("id",id).
				executeUpdate();
	}
	public void setInventory(int id,int quantity){
		em().createQuery(SET_INVENTORY).
				setParameter("id",id).
				setParameter("quantity",quantity).
				executeUpdate();
	}
}