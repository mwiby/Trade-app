package com.crypto.trading.service;

import com.crypto.trading.modal.Asset;
import com.crypto.trading.modal.Coin;
import com.crypto.trading.modal.User;

import java.util.List;

public interface AssetService {

    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetById(long assetId);

    Asset getAssetByUserIdAndId(Long userId, Long assetId);

    List<Asset> getUsersAssets(Long userId);

    Asset updateAsset(Long assetId, double quantity);

    Asset findAssetByUserIdAndCoinId(Long userId, Long coinId);

    void deleteAsset(Long assetId);

}
