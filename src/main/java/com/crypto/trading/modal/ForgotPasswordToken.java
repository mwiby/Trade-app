package com.crypto.trading.modal;


import com.crypto.trading.domain.Verification;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private User user;

    private String otp;
    private Verification verification;

    private String sendTo;




}
