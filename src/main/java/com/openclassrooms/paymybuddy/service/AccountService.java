package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.model.CardTypeProvider;
import com.openclassrooms.paymybuddy.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    UserService userService;

    public void addAccount(Account account, String email) {
        Integer uid = userService.getUidByEmail(email);
        if(uid == null) throw new IllegalArgumentException("Invalid user id");
        account.setCardType(null);
        switch (Integer.parseInt(account.getCardNumber().substring(0, 1))) {
            case 4:
                account.setCardType(CardTypeProvider.Visa);
                break;
            case 5:
                account.setCardType(CardTypeProvider.MasterCard);
                break;
            case 3:
                account.setCardType(CardTypeProvider.AmericanExpress);
                break;
        }
        if(account.getCardType() == null) throw new IllegalArgumentException("Invalid card number");
        else {
            account.setUid(uid);
            account.setModifiedDate(new Timestamp(System.currentTimeMillis()));
            accountRepository.save(account);
        }
    }

    public List<Account> getAccountsByEmail(String email) {
        List<Account> accounts = new ArrayList<>();
        Integer uid = userService.getUidByEmail(email);
        accounts.addAll(accountRepository.findAllByUid(uid));
        return accounts;
    }

    public void supplyAccount(double amount, int aid) {
        Account account = accountRepository.findByAid(aid);
        userService.addMoney(amount, account.getUid());
    }

    public void removeAccount(int aid) {
        accountRepository.deleteById(aid);
    }
}
