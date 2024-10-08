package com.crypto.trading.service;

import com.crypto.trading.modal.Order;
import com.crypto.trading.modal.User;
import com.crypto.trading.modal.Wallet;


public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long sum);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User user,Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;

}
