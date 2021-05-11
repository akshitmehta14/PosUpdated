package com.akshit.sale.dto;

import com.akshit.sale.model.BrandData;
import com.akshit.sale.model.BrandForm;
import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.BrandService;
import com.akshit.sale.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandDto {
    @Autowired
    private BrandService service;

    public List<BrandData> getAll() {
        return convert(service.getAll());
    }
    public BrandData select(int id) {
        return convert(service.select(id));
    }

    public void add(BrandForm form) throws ApiException {
        form = normalize(form);
        BrandDetail p = convert(form);

        if(StringUtil.isEmpty(p.getCategory()) || StringUtil.isEmpty(p.getBrand())){
            throw new ApiException("One or more fields are empty.");
        }
        service.add(p);
    }

    public void update(int id, BrandForm form) throws ApiException {

        form = normalize(form);
        BrandDetail p = convert(form);
        service.update(id,p);
    }

    private BrandForm normalize(BrandForm form) throws ApiException {
        if(StringUtil.isEmpty(form.getCategory()) || StringUtil.isEmpty(form.getBrand())){
            throw new ApiException("One or more fields are empty.");
        }
        form.setBrand(StringUtil.toLowerCaseTrim(form.getBrand()));
        form.setCategory(StringUtil.toLowerCaseTrim(form.getCategory()));
        return form;
    }

    private BrandDetail convert(BrandForm f) {
        BrandDetail p = new BrandDetail();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }
    private List<BrandData> convert(List<BrandDetail> list){
        List<BrandData> data = new ArrayList<BrandData>();
        for(BrandDetail temp:list){
            BrandData i = convert(temp);
            data.add(i);
        }
        return data;
    }
    private BrandData convert(BrandDetail b){
        BrandData ans = new BrandData();
        ans.setBrand_id(b.getBrand_id());
        ans.setBrand(b.getBrand());
        ans.setCategory(b.getCategory());
        return ans;
    }
}
