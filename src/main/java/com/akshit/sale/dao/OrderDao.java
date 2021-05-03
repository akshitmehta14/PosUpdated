package com.akshit.sale.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.akshit.sale.pojo.OrderItem;

import java.util.List;

@Repository
public class OrderDao extends AbstractDao{
	private static String select_all = "select p from OrderItem p";
	private static String selectbyid  = "Select p from OrderItem p where orderpojoid = :id";

	@PersistenceContext
	private EntityManager em;
	@Transactional
	public void add(OrderItem x) {
		em.persist(x);
	}
	public List<OrderItem> select(int id){
		TypedQuery<OrderItem> query = getQuery(selectbyid,OrderItem.class);
		query.setParameter("id",id);
		return query.getResultList();
	}
//	@Transactional
	public List<OrderItem> selectAll() {
		TypedQuery<OrderItem> query = getQuery(select_all, OrderItem.class);
		return query.getResultList();
	}


}
