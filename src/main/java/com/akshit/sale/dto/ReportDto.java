package com.akshit.sale.dto;
import com.akshit.sale.model.FilterForm;
import com.akshit.sale.model.SalesData;
import com.akshit.sale.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportDto {
    @Autowired
    private ReportService reportservice;

    public List<SalesData> salesgeneration(FilterForm form){
        form.setBrand(StringUtil.toLowerCase(form.getBrand()));
        form.setCategory(StringUtil.toLowerCase(form.getCategory()));
        return reportservice.salesgeneration(form);
    }
}
