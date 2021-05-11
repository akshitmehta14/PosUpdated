package com.akshit.sale.config;

import com.akshit.sale.pojo.BrandDetail;
import com.akshit.sale.pojo.Inventory;
import com.akshit.sale.pojo.Product;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.BrandService;
import com.akshit.sale.service.InventoryService;
import com.akshit.sale.service.ProductService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QaConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration("src/test/webapp")
@Transactional
public abstract class AbstractUnit {


    @Autowired
    protected BrandService brandService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected InventoryService inventoryService;


    protected List<String> barcodes;

    protected List<BrandDetail> brands;
    protected List<Product> products;
    protected List<Inventory> inventories;


    protected void createTestSchema() throws ApiException {
        barcodes = new ArrayList<String>();
        brands = new ArrayList<BrandDetail>();
        products = new ArrayList<Product>();
        inventories = new ArrayList<Inventory>();

        for (int i = 0; i < 2; i++) {
            BrandDetail brand = new BrandDetail();
            brand.setBrand("brand");
            brand.setCategory("category" + i);
            brandService.add(brand);
            brands.add(brand);

            Product product = new Product();
            product.setBrand_id(brand.getBrand_id());
            product.setName("product" + i);
            product.setBarcode("abc" + i);
            product.setMrp(50);
            productService.add(product);
            products.add(product);
            barcodes.add(product.getBarcode());

            Inventory inventory = new Inventory();
            inventory.setProduct_id(product.getProduct_id());
            inventory.setQuantity(20);
            inventoryService.add(inventory, product.getBarcode());
            inventories.add(inventory);
        }

        BrandDetail brand = new BrandDetail();
        brand.setBrand("brand");
        brand.setCategory("category3");
        brandService.add(brand);
        brands.add(brand);

        Product product = new Product();
        product.setBrand_id(brand.getBrand_id());
        product.setName("product3");
        product.setBarcode("abc3");
        product.setMrp(50);
        productService.add(product);
        products.add(product);
        barcodes.add(product.getBarcode());
    }

}
