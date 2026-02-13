package com.militaryasset.repository;

import com.militaryasset.model.Transfer;
import com.militaryasset.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
	List<Transfer> findByFromBase(Base base);
	List<Transfer> findByToBase(Base base);
	List<Transfer> findByFromBaseOrToBaseAndDateBetween(Base fromBase, Base toBase, LocalDate start, LocalDate end);
	List<Transfer> findByFromBaseOrToBase(Base fromBase, Base toBase);
}


