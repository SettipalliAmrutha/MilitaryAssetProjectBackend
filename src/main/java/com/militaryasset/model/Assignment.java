package com.militaryasset.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private int quantity;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedTo;  

    private boolean expended; 


    public Assignment() {
    }

    public Assignment(Asset asset, int quantity, LocalDate date, User assignedTo, boolean expended) {
        this.asset = asset;
        this.quantity = quantity;
        this.date = date;
        this.assignedTo = assignedTo;
        this.expended = expended;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }
}

