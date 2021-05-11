package com.akshit.sale.dao;

import com.akshit.sale.pojo.OrderItem;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {


    private static String SELECT_ALL = "select p from OrderItem p";
    private static String SELECT_BY_ID = "Select p from OrderItem p where orderpojoid = :id";


    @Transactional
    public void add(OrderItem x) {
        em().persist(x);
    }

    public List<OrderItem> select(int id) {
        TypedQuery<OrderItem> query = getQuery(SELECT_BY_ID, OrderItem.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Transactional
    public List<OrderItem> selectAll() {
        TypedQuery<OrderItem> query = getQuery(SELECT_ALL, OrderItem.class);
        return query.getResultList();
    }


}
