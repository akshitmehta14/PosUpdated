package com.akshit.sale.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.akshit.sale.pojo.OrderItem;
@Repository
public class OrderDao {
	@PersistenceContext
	private EntityManager em;
	@Transactional
	public void add(OrderItem x) {
		em.persist(x);
	}

}
