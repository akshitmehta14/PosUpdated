package com.akshit.sale.model;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
public class OrderInvoiceXmlList {


    private double total;

    @XmlElement(name="total")
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    private String date;
    @XmlElement(name="date")
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    private List<InvoiceModel> invoicelist;
    @XmlElement(name="item")
    public List<InvoiceModel> getInvoiceLis() {
        return invoicelist;
    }
    public void setInvoiceLis(List<InvoiceModel> invoicelist) {
        this.invoicelist = invoicelist;
    }


}