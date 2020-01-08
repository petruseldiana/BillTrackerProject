package com.proiect.inginerie.software.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue
    private Long id;
    private Double price;
    private String name;
    private Date due;
    private Boolean status;
    private String details;
    private Date creationDate;
    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Bill{" + "id=" + id + ", name='" + name + '\'' + ", due=" + due + ", status=" + status + ", details='" + details + '\'' + ", creationDate=" + creationDate + ", user=" + user + '}';
    }
}
