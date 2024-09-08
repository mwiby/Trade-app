package com.crypto.trading.controller;

import com.crypto.trading.domain.Verification;
import com.crypto.trading.modal.User;
import com.crypto.trading.modal.VerificationCode;
import com.crypto.trading.service.EmailService;
import com.crypto.trading.service.UserService;
import com.crypto.trading.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/api/user/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/user/verification/{verification}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Verification verification) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.
                getVerificationByUser(user.getId());
        if(verificationCode == null) {
            verificationCode = verificationCodeService
                    .sendVerificationCode(user,verification);
        }
        if(verification.equals(Verification.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }
        return new ResponseEntity<>("Verification otp sent successfully", HttpStatus.OK);
    }


    @PatchMapping("/api/user/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationByUser(user.getId());

        String sendTo = verificationCode
                .getVerification()
                .equals(Verification.EMAIL) ? user.getEmail() : verificationCode.getMobile();


        boolean isVerified = verificationCode.getOtp().equals(otp);

        if(isVerified){
            User updatedUser = userService.enableTwoFactorAuthentication
                    (
                            verificationCode.getVerification()
                            ,sendTo
                            ,user
                    );

            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        throw new Exception("Wrong otp");
    }

    @PostMapping("/auth/user/reset-password/send-otp")
    public ResponseEntity<String> sendForgotPasswordOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Verification verification) throws Exception {


        return new ResponseEntity<>("reset password sent success", HttpStatus.OK);
    }




}
