package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.TransactionDto;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.userService = userService;
    }

    public List<Transaction> getTransactionsByUid(Integer uid) {
        List<Transaction> res = new ArrayList<>();
        res.addAll(transactionRepository.findByPayer(uid));
        res.addAll(transactionRepository.findByPaid(uid));
        return res;
    }

    public boolean addTransaction(String payerEmail, Integer paid, String label, double amount) {
        Integer payer = userService.findUserByEmail(payerEmail).getUid();
        double truncatedAmount = truncateDouble(amount);
        double truncatedAmountWithFees = truncateDouble(amount * (1.005));

        boolean res = userService.addMoney(truncatedAmountWithFees * (-1), payer);
        if(res) {
            Transaction transaction = new Transaction();
            transaction.setPayer(payer);
            transaction.setPaid(paid);
            transaction.setLabel(label);
            transaction.setDate(new Timestamp(System.currentTimeMillis()));
            transaction.setPrice(truncatedAmountWithFees);
            transactionRepository.save(transaction);
            userService.addMoney(truncatedAmount, paid);
        }
        return res;
    }

    public List<TransactionDto> getHistory(String email) {
        List<TransactionDto> history = null;
        Integer uid = userService.findUserByEmail(email).getUid();
        List<Transaction> transactions = transactionRepository.findByPayerOrPaidOrderByDateDesc(uid, uid);
        User user = null;
        if(!transactions.isEmpty())
        {
            history = new ArrayList<>();
            for (Transaction t : transactions) {
                if(t.getPayer() == uid) {
                    //Viewer is payer
                    user = userService.findUserByUid(t.getPaid());
                    history.add(new TransactionDto(t.getPaid(), user.getFirstName(), user.getLastName(), t.getDate(), t.getPrice() * (-1), t.getLabel()));
                } else {
                    //Viewer is paye
                    user = userService.findUserByUid(t.getPayer());
                    history.add(new TransactionDto(t.getPayer(), user.getFirstName(), user.getLastName(), t.getDate(), t.getPrice(), t.getLabel()));
                }
            }
        }
        return history;
    }

    public static Double truncateDouble(double oldValue) {
        return BigDecimal.valueOf(oldValue).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static Sort.Direction getSortDirection(String rawDir) {
        if("asc".equalsIgnoreCase(rawDir)) {
            return Sort.Direction.ASC;
        } else if ("desc".equalsIgnoreCase(rawDir)) {
            return Sort.Direction.DESC;
        } else {
            throw new IllegalArgumentException("Wrong sort direction");
        }
    }

    public Page<TransactionDto> getPage(String email, Pageable pagingSort) {
        int uid = userService.getUidByEmail(email);
        Page<Transaction> pageTransactions;
        pageTransactions = transactionRepository.findByPayerOrPaid(uid, uid, pagingSort);

        List<TransactionDto> transactionsDto = null;
        User user = null;
        if(!pageTransactions.getContent().isEmpty())
        {
            transactionsDto = new ArrayList<>();
            for (Transaction t : pageTransactions.getContent()) {
                if(t.getPayer() == uid) {
                    //Viewer is payer
                    user = userService.findUserByUid(t.getPaid());
                    transactionsDto.add(new TransactionDto(t.getPaid(), user.getFirstName(), user.getLastName(), t.getDate(), t.getPrice() * (-1), t.getLabel()));
                } else {
                    //Viewer is paye
                    user = userService.findUserByUid(t.getPayer());
                    transactionsDto.add(new TransactionDto(t.getPayer(), user.getFirstName(), user.getLastName(), t.getDate(), t.getPrice(), t.getLabel()));
                }
            }
        }

        Page<TransactionDto> pageTransactionsDto = new PageImpl<TransactionDto>(transactionsDto,
                PageRequest.of(pagingSort.getPageNumber(), pagingSort.getPageSize()), transactionRepository.countByPayerOrPaid(uid, uid));
        return pageTransactionsDto;
    }

    public long getCountByEmail(String email) {
        int uid = userService.getUidByEmail(email);
        return transactionRepository.countByPayerOrPaid(uid, uid);
    }
}
