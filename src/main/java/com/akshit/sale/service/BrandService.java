package com.akshit.sale.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshit.sale.dao.BrandDao;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.model.BrandForm;
@Service
public class BrandService {

	@Autowired
	private BrandDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(BrandDetail p) throws ApiException {
		BrandForm f= new BrandForm();
		f.setBrand(p.getBrand());
		f.setCategory(p.getCategory());
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
		dao.update(id,f);
	}
	@Transactional
	public List<BrandDetail> getAll() {
		return dao.selectAll();
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
