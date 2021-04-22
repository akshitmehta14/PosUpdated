package com.akshit.sale.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.akshit.sale.pojo.OrderHistory;

@Repository
public class OrderHistoryDao extends AbstractDao {
	@PersistenceContext
	private EntityManager em;
	private static String select_all = "select p from OrderHistory p";
	@Transactional
	public int add(OrderHistory x) {
		em.persist(x);
		return x.getOrder_id();
	}
	public List<OrderHistory> getall() {
		TypedQuery<OrderHistory> query = getQuery(select_all, OrderHistory.class);
		return query.getResultList();
	}
	
}
