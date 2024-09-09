package com.crypto.trading.request;

import com.crypto.trading.domain.Verification;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {

    private String sendTo;
    private Verification verification;
}
