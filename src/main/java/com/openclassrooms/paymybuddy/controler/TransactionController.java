package com.openclassrooms.paymybuddy.controler;

import com.openclassrooms.paymybuddy.configuration.SecurityConfiguration;
import com.openclassrooms.paymybuddy.dto.FriendDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.openclassrooms.paymybuddy.configuration.SecurityConfiguration.getEmailFromAuthentication;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/transfer")
    public String transferPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        Transaction transaction = new Transaction();
        List<TransactionDto> history = transactionService.getHistory(email);
        List<FriendDto> friendList = userService.getFriendDtoList(email);
        model.addAttribute("transaction", transaction);
        model.addAttribute("history", history);
        model.addAttribute("friendList", friendList);
        return "transfer";
    }

    @PostMapping("/addTransaction")
    public String addTransfer(@ModelAttribute("transaction") Transaction transaction, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        if (transaction.getPrice() < 0 || transaction.getPrice() == 0)
            result.rejectValue("price", null,
                    "You must send something...");
        transactionService.addTransaction(email, transaction.getPaid(), transaction.getLabel(), transaction.getPrice());
        return "redirect:/transfer?success";
    }
}
