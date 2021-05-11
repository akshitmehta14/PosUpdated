package com.akshit.sale.dto;

import com.akshit.sale.model.InventoryData;
import com.akshit.sale.model.InventoryForm;
import com.akshit.sale.pojo.Inventory;
import com.akshit.sale.service.ApiException;
import com.akshit.sale.service.InventoryService;
import com.akshit.sale.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryDto {
    @Autowired
    private InventoryService service;

    public void add(InventoryForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getBarcode())) {
            throw new ApiException("Barcode cannot be empty");
        }
        form = normalizeCheck(form);
        Inventory i = convert(form);
        service.add(i, form.getBarcode());
    }

    public void update(String barcode, int quantity) throws ApiException {
        if (StringUtil.isEmpty(barcode)) {
            throw new ApiException("Barcode cannot be empty");
        }

        barcode = StringUtil.toLowerCaseTrim(barcode);
        service.update(barcode, quantity);
    }

    public InventoryData select(String barcode) throws ApiException {
        barcode = StringUtil.toLowerCaseTrim(barcode);
        if (StringUtil.isEmpty(barcode)) {
            throw new ApiException("Barcode cannot be empty");
        }
        return service.select(barcode);
    }

    public List<InventoryData> getAll() {
        return service.getAll();
    }

    private InventoryForm normalizeCheck(InventoryForm p) throws ApiException {
        if (StringUtil.negative(p.getQuantity()) == true) {
            throw new ApiException("quantity cannot be negative");
        }

        if (StringUtil.isEmpty(p.getBarcode()) == true) {
            throw new ApiException("barcode cannot be empty");
        }
        p.setBarcode(StringUtil.toLowerCaseTrim(p.getBarcode()));
        return p;
    }

    protected Inventory convert(InventoryForm form) {
        Inventory converted = new Inventory();
        converted.setQuantity(form.getQuantity());
        return converted;
    }
}
