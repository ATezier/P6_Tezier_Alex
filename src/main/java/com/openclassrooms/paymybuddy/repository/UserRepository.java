package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByEmail(String email);

    public User findByFirstNameAndLastName(String FirstName, String LastName);

    public User findByUid(Integer uid);
}
