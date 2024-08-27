package com.crypto.trading.modal;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private boolean status;
    private String message;
    private boolean isTwoFactorAuthentication;
    private String session;
}
