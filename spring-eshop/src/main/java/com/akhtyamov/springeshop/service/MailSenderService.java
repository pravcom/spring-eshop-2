package com.akhtyamov.springeshop.service;

import com.akhtyamov.springeshop.domain.User;

public interface MailSenderService {
    void sendActivateCode(User user);
}
