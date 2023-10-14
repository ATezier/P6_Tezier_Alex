package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Buddy;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuddyRepository extends CrudRepository<Buddy, Integer> {
    public List<Buddy> findAllByUid1OrUid2(Integer uid1, Integer uid2);
    public Boolean existsByUid1AndUid2(Integer uid1, Integer uid2);
    public Buddy findByUid1AndUid2(Integer uid1, Integer uid2);
}
