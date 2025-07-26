package com.personal.bankapp.service.impl;

import com.personal.bankapp.dto.BankResponse;
import com.personal.bankapp.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}
