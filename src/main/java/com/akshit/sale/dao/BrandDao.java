package com.akshit.sale.dao;


import com.akshit.sale.pojo.BrandDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao {
    private static String SELECT = "select p from BrandDetail p where p.brand=:brand AND p.category=:category";
    private static String SELECT_BY_ID = "select p from BrandDetail p where p.brand_id = :id";
    private static String SELECT_ALL = "select p from BrandDetail p";
    private static String UPDATE_BY_ID = "update BrandDetail set brand=:brand , category=:category where brand_id=:id";


    @Transactional
    public void insert(BrandDetail p) {
        em().persist(p);
    }

    @Transactional
    public void update(int id, BrandDetail p) {
        em().createQuery(UPDATE_BY_ID).
                setParameter("id", id).
                setParameter("brand", p.getBrand()).
                setParameter("category", p.getCategory()).
                executeUpdate();
    }

    @Transactional
    public BrandDetail select(int id) {
        TypedQuery<BrandDetail> query = getQuery(SELECT_BY_ID, BrandDetail.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public BrandDetail select(BrandDetail p) {
        TypedQuery<BrandDetail> query = getQuery(SELECT, BrandDetail.class);
        query.setParameter("brand", p.getBrand()).setParameter("category", p.getCategory());
        return getSingle(query);
    }

    public List<BrandDetail> selectAll() {
        TypedQuery<BrandDetail> query = getQuery(SELECT_ALL, BrandDetail.class);
        return query.getResultList();
    }

}
