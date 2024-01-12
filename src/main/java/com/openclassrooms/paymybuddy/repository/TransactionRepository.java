package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {
    public List<Transaction> findByPayer(Integer payer);

    public List<Transaction> findByPaid(Integer paid);

    public Page<Transaction> findByPayerOrPaid(@NonNull Integer payer, @NonNull Integer paid, Pageable pageable);

    public List<Transaction> findByPayerOrPaidOrderByDateDesc(@NonNull Integer payer, @NonNull Integer paid);

    public Transaction save(Transaction transaction);

    public long countByPayerOrPaid(Integer payer, Integer paid);
}
