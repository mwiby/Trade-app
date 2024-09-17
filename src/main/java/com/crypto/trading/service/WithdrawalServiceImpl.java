package com.crypto.trading.service;

import com.crypto.trading.modal.User;
import com.crypto.trading.modal.Withdrawal;
import com.crypto.trading.modal.WithdrawalStatus;
import com.crypto.trading.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    public WithdrawalRepository withdrawalRepository;



    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);
        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal processWithdrawal(Long withdrawalId, boolean accepted) throws Exception {
        Optional<Withdrawal> withdrawalOpt = withdrawalRepository.findById(withdrawalId);
        if (withdrawalOpt.isEmpty()) {
            throw new Exception("withdrawal not found");
        }
        Withdrawal withdrawal = withdrawalOpt.get();

        withdrawal.setDate(LocalDateTime.now());

        if(accepted) {
            withdrawal.setStatus(WithdrawalStatus.SUCCESS);
        }
        else {
            withdrawal.setStatus(WithdrawalStatus.PENDING);
        }

        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
        return withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepository.findAll();
    }
}
