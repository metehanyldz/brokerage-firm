package com.ing.brokerage.brokerage_firm.repository;

import com.ing.brokerage.brokerage_firm.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
}
