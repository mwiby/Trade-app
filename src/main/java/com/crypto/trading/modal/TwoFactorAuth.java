package com.crypto.trading.modal;

import com.crypto.trading.domain.Verification;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private Verification sendTo;
}
