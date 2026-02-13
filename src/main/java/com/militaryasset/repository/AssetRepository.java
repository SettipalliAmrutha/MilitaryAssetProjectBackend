package com.militaryasset.repository;

import com.militaryasset.model.Asset;
import com.militaryasset.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByBase(Base base);
}
