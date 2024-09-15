package com.crypto.trading.repository;

import com.crypto.trading.modal.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByUserId(long userId);

    Asset findByUserIdAndCoinId(long userId, String coinId);

}
