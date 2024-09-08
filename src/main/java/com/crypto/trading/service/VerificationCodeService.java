package com.crypto.trading.service;

import com.crypto.trading.domain.Verification;
import com.crypto.trading.modal.User;
import com.crypto.trading.modal.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, Verification verificationCode);

    VerificationCode getVerificationCode(Long id) throws Exception;

    VerificationCode getVerificationByUser(Long id);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
