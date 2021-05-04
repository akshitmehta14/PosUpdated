package com.akshit.sale.controller;

import com.akshit.sale.dao.OrderHistoryDao;
import com.akshit.sale.model.InvoiceModel;
import com.akshit.sale.model.OrderData;
import com.akshit.sale.model.OrderInvoiceXmlList;
import com.akshit.sale.service.OrderService;
import com.akshit.sale.util.PDFConversion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class PdfApiController{
    @Autowired
    OrderService service;
    @Autowired
    OrderHistoryDao dao;

    @ApiOperation(value = "Generate invoice")
    @RequestMapping(path = "/api/pdf/{id}", method = RequestMethod.GET)
    public void generatepdf(@PathVariable int id, HttpServletResponse response) throws Exception {
        LocalDateTime time = dao.select(id).getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = time.format(formatter);
        List<OrderData> items = service.select(id);
        List<InvoiceModel> invoice = new ArrayList<InvoiceModel>();
        for(OrderData i:items){
            InvoiceModel m = new InvoiceModel();
            m.setMrp(i.getMrp());
            m.setName(i.getName());
            m.setQuantity(i.getQuantity());
            invoice.add(m);
        }
        byte[] bytes = generatePdfResponse(invoice,date);
        createPdfResponse(bytes, response);
    }
    public byte[] generatePdfResponse(List<InvoiceModel> list,String date) throws Exception {
        OrderInvoiceXmlList idl= new OrderInvoiceXmlList();
        idl.setInvoiceLis(list);
        idl.setTotal(findtotal(list));
        idl.setDate(date);
        PDFConversion.generateXml(new File("invoice.xml"), idl, OrderInvoiceXmlList.class);
        return PDFConversion.generatePDF(new File("invoice.xml"), new StreamSource("invoice.xsl"));

    }
    public double findtotal(List<InvoiceModel> list){
        int total = 0;
        for(InvoiceModel i:list){
            double additn = i.getQuantity()*i.getMrp();
            total+=additn;
        }
        return total;
    }
    public void createPdfResponse(byte[] bytes, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);

        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }
}
