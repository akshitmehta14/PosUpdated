package com.akshit.sale.service;

import com.akshit.sale.config.AbstractUnit;
import com.akshit.sale.pojo.BrandDetail;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BrandTest extends AbstractUnit {


//    Test for add(), get by id and getAll() in service layer
    @Test
    public void testAdd() throws ApiException {

        BrandDetail p = getBrandPojo();
        List<BrandDetail> brand_list_before = brandService.getAll();
        brandService.add(p);
        List<BrandDetail> brand_list_after = brandService.getAll();
        BrandDetail pojo= brandService.select(p.getBrand_id());
        // Number of brands should increase
        assertEquals(brand_list_before.size() + 1, brand_list_after.size());
        assertEquals(p.getBrand(), pojo.getBrand());
        assertEquals(p.getCategory(), pojo.getCategory());
    }

    @Test
    public void testQueries() throws ApiException{
        BrandDetail p = getBrandPojo();
        brandService.add(p);
        BrandDetail pojo= brandService.select(p.getBrand_id());

        assertEquals(p.getBrand(), pojo.getBrand());
        assertEquals(p.getCategory(), pojo.getCategory());
    }

    @Test()
    public void testAddDuplicate() throws ApiException {

        BrandDetail p = getBrandPojo();
        brandService.add(p);

        try {
            brandService.add(p);
            fail("Api Exception did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Brand and Category combination already exists");
        }

    }

    /* Testing adding of invalid brand pojo. Exception should be thrown */


    @Test
    public void testCheck() throws ApiException {
        BrandDetail p = getBrandPojo();

        try {
            brandService.select(p);
            fail("Api Exception did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Brand Category combination does not exist.");
        }
    }

    private BrandDetail getBrandPojo() {
        BrandDetail p = new BrandDetail();
        p.setBrand("Parle");
        p.setCategory("Biscuits");
        return p;
    }
}

