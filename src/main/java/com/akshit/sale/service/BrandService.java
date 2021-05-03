package com.akshit.sale.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import com.akshit.sale.dto.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshit.sale.dao.BrandDao;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.model.BrandForm;
@Service
public class BrandService<list> {

	@Autowired
	private BrandDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(BrandDetail p) throws ApiException {
		BrandForm f= new BrandForm();
		f.setBrand(StringUtil.toLowerCase(p.getBrand()));
		f.setCategory(StringUtil.toLowerCase(p.getCategory()));


		BrandDetail b = dao.select(f);
		if(b!=null) {
			throw new ApiException("Brand and Category combination already exists");
		}
		dao.insert(p);
	}

	/*
	 * @Transactional(rollbackOn = ApiException.class) public EmployeePojo get(int
	 * id) throws ApiException { return getCheck(id); }
	 */
	@Transactional
	public void update(int id,BrandForm f){
		f.setBrand(StringUtil.toLowerCase(f.getBrand()));
		f.setCategory(StringUtil.toLowerCase(f.getCategory()));
		dao.update(id,f);
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

	/*
	 * @Transactional(rollbackOn = ApiException.class) public void update(int id,
	 * EmployeePojo p) throws ApiException { normalize(p); EmployeePojo ex =
	 * getCheck(id); ex.setAge(p.getAge()); ex.setName(p.getName()); dao.update(ex);
	 * }
	 */

	/*
	 * @Transactional public BrandDetail getCheck(BrandForm f) throws ApiException {
	 * EmployeePojo p = dao.select(f); if (p == null) { throw new
	 * ApiException("Employee with given ID does not exit, id: " + id); } return p;
	 * }
	 * 
	 * protected static void normalize(EmployeePojo p) {
	 * p.setName(StringUtil.toLowerCase(p.getName())); }
	 */
}
