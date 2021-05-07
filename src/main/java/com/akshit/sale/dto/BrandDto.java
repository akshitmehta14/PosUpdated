package com.akshit.sale.dto;

import com.akshit.sale.model.BrandForm;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandDto {
    @Autowired
    private BrandService service;

    public List<BrandDetail> getAll() {
        return service.getAll();
    }
    public BrandDetail select(int id) {

        return service.select(id);
    }

    public void add(BrandForm form) throws ApiException {
        BrandDetail p = convert(form);
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
        if(StringUtil.isEmpty(p.getCategory()) || StringUtil.isEmpty(p.getBrand())){
            throw new ApiException("One or more fields are empty.");
        }
        service.add(p);
    }

    public void update(int id, BrandForm f) throws ApiException {

        f.setBrand(StringUtil.toLowerCase(f.getBrand()));
        f.setCategory(StringUtil.toLowerCase(f.getCategory()));
        if(StringUtil.isEmpty(f.getCategory()) || StringUtil.isEmpty(f.getBrand())){
            throw new ApiException("One or more fields are empty.");
        }
        BrandDetail p = convert(f);
        service.update(id,p);
    }

    private static BrandForm convert(BrandDetail p) {
        BrandForm d = new BrandForm();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }

    private static BrandDetail convert(BrandForm f) {
        BrandDetail p = new BrandDetail();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }


}
