package com.crypto.trading.controller;

import com.crypto.trading.modal.User;
import com.crypto.trading.modal.Wallet;
import com.crypto.trading.modal.WalletTransaction;
import com.crypto.trading.service.UserService;
import com.crypto.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    private ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);

    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req
            ) throws Exception {

        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(
                senderUser,
                receiverWallet,
                req.getAmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}
