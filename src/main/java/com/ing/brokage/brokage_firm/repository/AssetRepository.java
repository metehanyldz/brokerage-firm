package com.ing.brokage.brokage_firm.repository;

import com.ing.brokage.brokage_firm.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
}
