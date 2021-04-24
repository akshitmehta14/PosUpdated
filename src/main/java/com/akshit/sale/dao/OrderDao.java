package com.akshit.sale.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.akshit.sale.pojo.OrderItem;

import java.util.List;

@Repository
public class OrderDao {
	private static String select_all = "select p from OrderItem p";

	@PersistenceContext
	private EntityManager em;
	@Transactional
	public void add(OrderItem x) {
		em.persist(x);
	}

	@Transactional
	public List<OrderItem> selectAll() {
		TypedQuery<OrderItem> query = getQuery(select_all, OrderItem.class);
		return query.getResultList();
	}
}
