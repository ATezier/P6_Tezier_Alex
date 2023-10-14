package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Buddy;
import com.openclassrooms.paymybuddy.repository.BuddyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuddyService {
    @Autowired
    private BuddyRepository buddyRepository;

    public List<Integer> getBuddyList(Integer uid) {
        List<Integer> res = new ArrayList<>();
        List<Buddy> buddies = buddyRepository.findAllByUid1OrUid2(uid, uid);
        for (Buddy buddy : buddies) {
            if(buddy.getUid1() == uid) {
                res.add(buddy.getUid2());
            } else {
                res.add(buddy.getUid1());
            }
        }
        return res;
    }

    public Boolean addBuddy(Integer uid1, Integer uid2) {
        Boolean res = false;
        if(!buddyRepository.existsByUid1AndUid2(uid1, uid2) && !buddyRepository.existsByUid1AndUid2(uid2, uid1)) {
            buddyRepository.save(new Buddy(uid1, uid2));
            res = true;
        }
        return res;
    }

    public Boolean removeBuddy(Integer uid1, Integer uid2) {
        Boolean res = false;
        Buddy buddy = null;
        if(buddyRepository.existsByUid1AndUid2(uid1, uid2)) {
            buddy = buddyRepository.findByUid1AndUid2(uid1, uid2);
            buddyRepository.delete(buddy);
            res = true;
        }
        if(buddyRepository.existsByUid1AndUid2(uid2, uid1)) {
            buddy = buddyRepository.findByUid1AndUid2(uid2, uid1);
            buddyRepository.delete(buddy);
            res = true;
        }
        return res;
    }
}
