package com.akshit.sale.service;

import com.akshit.sale.dao.BrandDao;
import com.akshit.sale.pojo.BrandDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
@Service
public class BrandService {

	@Autowired
	private BrandDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(BrandDetail p) throws ApiException {

		BrandDetail b = dao.select(p);
		if(b!=null) {
			throw new ApiException("Brand and Category combination already exists");
		}
		dao.insert(p);
	}

	@Transactional
	public void update(int id,BrandDetail p) throws ApiException {
		if(dao.select(p)!=null){
			throw new ApiException("Brand and Category combination already exists");
		}
		dao.update(id,p);
	}
	@Transactional
	public List<BrandDetail> getAll() {
		List<BrandDetail> list = dao.selectAll();
		Collections.sort(list , (o1, o2) -> (o1.getBrand_id() > o2.getBrand_id()) ? 1 : (o1.getBrand_id() < o2.getBrand_id()) ? -1 : 0);
		return list;
	}

	public BrandDetail select(int id) {
		return dao.select(id);
	}
	public int select(BrandDetail b) throws ApiException {
		BrandDetail selected = dao.select(b);
		if(selected==null){
			throw new ApiException("Brand Category combination does not exist.");
		}
		return selected.getBrand_id();
	}
}
