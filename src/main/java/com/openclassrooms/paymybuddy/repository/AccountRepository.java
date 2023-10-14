package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Account;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    public List<Account> findByUid(Integer uid);

    public List<Integer> findAidByUid(Integer uid);
}
