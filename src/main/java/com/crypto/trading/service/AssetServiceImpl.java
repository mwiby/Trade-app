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
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());

        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(long assetId) throws Exception {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new Exception("Asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAssets(Long userId) {
        return assetRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {

        Asset newAsset = getAssetById(assetId);
        newAsset.setQuantity(quantity + newAsset.getQuantity());
        return assetRepository.save(newAsset);

    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepository.findByUserIdAndCoinId(userId, coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);
    }
}
