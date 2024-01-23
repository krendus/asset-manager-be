package com.eprocess.assetmanager.repositories;

import com.eprocess.assetmanager.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> getAssetByIdAndUserId(Long assetId, Long userId);
}
