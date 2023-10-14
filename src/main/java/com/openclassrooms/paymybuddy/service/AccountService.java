package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Integer> getAidsByUid(Integer uid) {
        return accountRepository.findAidByUid(uid);
    }
}
