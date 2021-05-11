package com.akshit.sale.service;

import com.akshit.sale.config.AbstractUnit;
import com.akshit.sale.model.InventoryData;
import com.akshit.sale.pojo.Inventory;
import com.akshit.sale.pojo.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InventoryServiceTest extends AbstractUnit {


    @Before
    public void Initialize() throws ApiException {
        createTestSchema();
    }

    @Test
    public void testAdd() throws ApiException {

        Inventory i = getInventoryPojo(products.get(2));
        List<InventoryData> inv_list_before = inventoryService.getAll();
        inventoryService.add(i, "abc3");
        List<InventoryData> inv_list_after = inventoryService.getAll();
        assertEquals(inv_list_before.size() + 1, inv_list_after.size());
        assertEquals(i.getProduct_id(), inventoryService.select(i.getProduct_id()).getProduct_id());
        assertEquals(i.getQuantity(), inventoryService.select(i.getProduct_id()).getQuantity());

    }


    /* Testing adding of an invalid pojo. Should throw exception */
    @Test()
    public void testAddWrong() throws ApiException {

        Inventory i = getWrongInventoryPojo(products.get(0));

        try {
            inventoryService.add(i, "abc3");
            fail("ApiException did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Quantity cannot be negative");
        }

    }


    /* Testing checkifexists with an non-existent id. Should throw exception */
    @Test()
    public void testNoProduct() throws ApiException {
        int id = 5;
        try {
            inventoryService.select(id);
            fail("ApiException did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "No such product exists");
        }
    }

    /* Testing get by id of a non-existent pojo. Should throw exception */
    @Test(expected = ApiException.class)
    public void testNoInventoryForProduct() throws ApiException {
        inventoryService.select("abc3");
    }

    @Test()
    public void testToGetAll() {
        List<InventoryData> inventory = new ArrayList<InventoryData>();
        inventory = inventoryService.getAll();
        assertEquals(inventory.size(), 2);
    }


    private Inventory getInventoryPojo(Product p) {
        Inventory i = new Inventory();
        i.setProduct_id(p.getProduct_id());
        i.setQuantity(20);
        return i;
    }

    private Inventory getWrongInventoryPojo(Product p) {
        Inventory i = new Inventory();
        i.setProduct_id(p.getProduct_id());
        i.setQuantity(-5);
        return i;
    }
}
