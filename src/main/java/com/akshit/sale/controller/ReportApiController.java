package com.akshit.sale.controller;

import java.util.List;

import com.akshit.sale.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.akshit.sale.dto.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.akshit.sale.dto.ReportDto;
@Api
@RestController
public class ReportApiController {
    @Autowired
    private ProductDto productdto;
    @Autowired
    private ReportDto reportdto;
    @ApiOperation(value = "Sales report")
    @RequestMapping("/api/report/sales")
    public List<SalesData> salesgeneration(@RequestBody FilterForm filters){
        return reportdto.salesgeneration(filters);
    }
}
