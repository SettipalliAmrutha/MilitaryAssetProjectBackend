package com.militaryasset.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private int quantity;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "from_base_id")
    private Base fromBase;

    @ManyToOne
    @JoinColumn(name = "to_base_id")
    private Base toBase;

    

    public Transfer() {
    }

    public Transfer(Asset asset, int quantity, LocalDate date, Base fromBase, Base toBase) {
        this.asset = asset;
        this.quantity = quantity;
        this.date = date;
        this.fromBase = fromBase;
        this.toBase = toBase;
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

    public Base getFromBase() {
        return fromBase;
    }

    public void setFromBase(Base fromBase) {
        this.fromBase = fromBase;
    }

    public Base getToBase() {
        return toBase;
    }

    public void setToBase(Base toBase) {
        this.toBase = toBase;
    }
}