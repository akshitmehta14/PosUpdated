package com.akshit.sale.dto;
import com.akshit.sale.model.FilterForm;
import com.akshit.sale.model.SalesData;
import com.akshit.sale.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.akshit.sale.util.StringUtil;
import java.util.List;

@Service
public class ReportDto {
    @Autowired
    private ReportService reportservice;

    public List<SalesData> generateSalesReport(FilterForm form){
        form.setBrand(StringUtil.toLowerCaseTrim(form.getBrand()));
        form.setCategory(StringUtil.toLowerCaseTrim(form.getCategory()));
        return reportservice.generateSalesReport(form);
    }
}
