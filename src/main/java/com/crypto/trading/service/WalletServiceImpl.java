package com.crypto.trading.service;

import com.crypto.trading.modal.Order;
import com.crypto.trading.modal.User;
import com.crypto.trading.modal.Wallet;
import com.crypto.trading.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;


    @Override
    public Wallet getUserWallet(User user) {
        return null;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long sumTotal) {
        return null;
    }

    @Override
    public Wallet findWalletById(Long id) {
        return null;
    }

    @Override
    public Wallet walletToWalletTransfer(User user, Wallet receiverWallet, Long amount) {
        return null;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) {
        return null;
    }
}
