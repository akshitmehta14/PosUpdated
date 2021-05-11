package com.akshit.sale.dao;

import com.akshit.sale.pojo.OrderHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderHistoryDao extends AbstractDao {

	private static String SELECT_BY_ID = "select p from OrderHistory p where order_id=:id";
	private static String SELECT_ALL = "select p from OrderHistory p";
	@Transactional
	public int add(OrderHistory x) {
		em().persist(x);
		return x.getOrder_id();
	}
	public List<OrderHistory> getall() {
		TypedQuery<OrderHistory> query = getQuery(SELECT_ALL, OrderHistory.class);
		return query.getResultList();
	}
	public OrderHistory select(int id){
		TypedQuery<OrderHistory> query = getQuery(SELECT_BY_ID,OrderHistory.class);
		query.setParameter("id",id);
		return getSingle(query);
	}
}
