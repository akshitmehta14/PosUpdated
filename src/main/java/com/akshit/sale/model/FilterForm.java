package com.akshit.sale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FilterForm {
    private LocalDateTime start;
    private LocalDateTime end;
    private String brand;
    private String category;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(String start) {
        if(start==null || start==""){
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime x= now.minusYears(2);
            this.start = x;
        }
        else {
            this.start = LocalDateTime.parse(start);
        }
    }

    public LocalDateTime getEnd() {

        return end;
    }

    public void setEnd(String end) {
        if(end==null || end==""){
        LocalDateTime now = LocalDateTime.now();

        this.end = now;
    }
        else {
            this.end = LocalDateTime.parse(end);
        }
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
