package com.militaryasset.repository;

import com.militaryasset.model.Purchase;
import com.militaryasset.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByBaseAndDateBetween(Base base, LocalDate start, LocalDate end);
    List<Purchase> findByBase(Base base);
    List<Purchase> findByBase_Id(Long baseId);

}