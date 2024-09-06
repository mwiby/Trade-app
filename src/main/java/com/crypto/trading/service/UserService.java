package com.crypto.trading.service;

import com.crypto.trading.domain.Verification;
import com.crypto.trading.modal.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(Verification verification,
                                              String sendTo,
                                              User user);

    User updatePassword(User user, String newPassword);
}
