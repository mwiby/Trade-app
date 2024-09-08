package com.crypto.trading.service;

import com.crypto.trading.domain.Verification;
import com.crypto.trading.modal.ForgotPasswordToken;
import com.crypto.trading.modal.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user,
                                    String id,
                                    String otp,
                                    Verification verification,
                                    String sendTo);

    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long otp);

    void deleteToken(ForgotPasswordToken token);

}
