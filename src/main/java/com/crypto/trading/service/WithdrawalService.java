package com.crypto.trading.service;

import com.crypto.trading.modal.User;
import com.crypto.trading.modal.Withdrawal;

import java.util.List;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal processWithdrawal(Long withdrawalId, boolean accepted) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();
}
