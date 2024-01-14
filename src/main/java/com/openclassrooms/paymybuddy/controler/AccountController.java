package com.openclassrooms.paymybuddy.controler;

import com.openclassrooms.paymybuddy.configuration.SecurityConfiguration;
import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/addFunds")
    public String accounts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        Double amount = 0.0;
        List<Account> accounts = accountService.getAccountsByEmail(email);
        model.addAttribute("accounts", accounts);
        model.addAttribute("amount", amount);
        return "addFunds";
    }

    @PostMapping("/addFunds")
    public String addFunds(
            @Valid @ModelAttribute("amount") Double amount, @Valid @ModelAttribute("aid") Integer aid,
            BindingResult result) {
        accountService.supplyAccount(amount, aid);
        return "redirect:/home?success";
    }

    @RequestMapping("/addAccount")
    public String accountForm(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "addAccount";
    }
    @PostMapping("/addAccount")
    public String addAccount(
            @Valid @ModelAttribute("account") Account account,
            BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        accountService.addAccount(account, email);
        return "redirect:/addFunds?success";
    }


}
