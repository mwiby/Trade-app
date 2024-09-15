package com.crypto.trading.service;

import com.crypto.trading.modal.Asset;
import com.crypto.trading.modal.Coin;
import com.crypto.trading.modal.User;
import com.crypto.trading.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        return null;
    }

    @Override
    public Asset getAssetById(long assetId) {
        return null;
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAssets(Long userId) {
        return List.of();
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) {
        return null;
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, Long coinId) {
        return null;
    }

    @Override
    public void deleteAsset(Long assetId) {

    }
}
