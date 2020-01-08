package com.proiect.inginerie.software.controller.dtos;

import java.sql.Date;

public class BillChartDto {
    private Date date;
    private Double prices;

    public BillChartDto(Date date, Double prices) {
        this.date = date;
        this.prices = prices;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrices() {
        return prices;
    }

    public void setPrices(Double prices) {
        this.prices = prices;
    }
}
