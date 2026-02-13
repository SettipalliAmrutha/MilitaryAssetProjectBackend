package com.militaryasset.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private int quantity;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "base_id")
    private Base base;  

   

    public Purchase() {
    }

    public Purchase(Asset asset, int quantity, LocalDate date, Base base) {
        this.asset = asset;
        this.quantity = quantity;
        this.date = date;
        this.base = base;
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

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }
}
