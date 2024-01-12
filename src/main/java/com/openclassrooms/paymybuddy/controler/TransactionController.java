package com.openclassrooms.paymybuddy.controler;

import com.openclassrooms.paymybuddy.configuration.SecurityConfiguration;
import com.openclassrooms.paymybuddy.dto.FriendDto;
import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.openclassrooms.paymybuddy.service.TransactionService.getSortDirection;

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

    @GetMapping("/testTransaction")
    public String getAllTutorialsPage(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "date,desc") String[] sort) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);

        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        Page<TransactionDto> pageTransactionsDto;
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        pageTransactionsDto = transactionService.getPage(email, pagingSort);
        int totalPages = pageTransactionsDto.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, (int) totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("transactions", pageTransactionsDto);
        //model.addAttribute("totalItems", pageTransactions.getTotalElements());
        return "/testTransaction";
    }
}
