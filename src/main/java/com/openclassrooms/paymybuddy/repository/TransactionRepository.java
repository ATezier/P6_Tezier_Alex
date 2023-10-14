package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    public List<Transaction> findByPayer(Integer payer);

    public List<Transaction> findByPaid(Integer paid);

    List<Transaction> findByPayerOrPaidOrderByDateDesc(@NonNull Integer payer, @NonNull Integer paid);

}
