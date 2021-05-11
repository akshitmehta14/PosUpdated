package com.akshit.sale.model;

import java.time.LocalDateTime;

public class OrderHistoryData {
    private int order_id;
    private LocalDateTime date;
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
