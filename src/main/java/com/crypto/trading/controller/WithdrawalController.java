package com.crypto.trading.controller;


import com.crypto.trading.modal.User;
import com.crypto.trading.modal.Wallet;
import com.crypto.trading.modal.WalletTransaction;
import com.crypto.trading.modal.Withdrawal;
import com.crypto.trading.repository.WalletRepository;
import com.crypto.trading.service.UserService;
import com.crypto.trading.service.WalletService;
import com.crypto.trading.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;
    @Autowired
    private WalletRepository walletRepository;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
}
