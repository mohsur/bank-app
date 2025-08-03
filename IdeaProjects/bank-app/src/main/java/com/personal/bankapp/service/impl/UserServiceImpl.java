package com.personal.bankapp.service.impl;

import com.personal.bankapp.dto.AccountInfo;
import com.personal.bankapp.dto.BankResponse;
import com.personal.bankapp.dto.EmailDetails;
import com.personal.bankapp.dto.UserRequest;
import com.personal.bankapp.entity.User;
import com.personal.bankapp.repository.UserRepository;
import com.personal.bankapp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if(userRepository.existsUserByEmail(userRequest.getEmail())){
                BankResponse bankResponse = BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                        .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                        .accountInfo(null)
                        .build();
            return bankResponse;
        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .address(userRequest.getAddress())
                .accountNumber(AccountUtils.generateAccountNumber())
                .email(userRequest.getEmail())
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

       User savedUser= userRepository.save(newUser);

       EmailDetails emailDetails = EmailDetails.builder()
               .recipientEmail(savedUser.getEmail())
               .subject("Account Created Successfully")
               .messageBody("Your account has been successfully created \n" +
                       "Your Account details \n"+
                       "Account Number" + savedUser.getAccountNumber() +"\n"+
                       "Account Name  " + savedUser.getFirstName() + " " + savedUser.getLastName())
               .build();
       emailService.sendEmail(emailDetails);
       return BankResponse.builder()
               .responseCode(AccountUtils.ACCOUNT_CREATED_MESSAGE)
               .responseMessage(AccountUtils.ACCOUNT_CREATED_MESSAGE)
               .accountInfo(AccountInfo.builder()
                       .accountBalance(savedUser.getAccountBalance())
                       .accountNumber(savedUser.getAccountNumber())
                       .accountName(savedUser.getFirstName()+" "+savedUser.getLastName()+" "+savedUser.getOtherName())
                       .build())
               .build();
    }
}
