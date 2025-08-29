package com.poseidon.IT.support;

import com.poseidon.domain.*;
import com.poseidon.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityTestUtils {

    @Autowired
    private BidListRepository bidListRepository;
    @Autowired private CurvePointRepository curvePointRepository;
    @Autowired private RatingRepository ratingRepository;
    @Autowired private RuleNameRepository ruleNameRepository;
    @Autowired private TradeRepository tradeRepository;
    @Autowired private UserRepository userRepository;

    public Boolean isPresentEntity(Object entity) {
        if (entity instanceof BidList bidList) {
            return bidListRepository.existsByAccount(bidList.getAccount());
        } else if (entity instanceof CurvePoint curvePoint) {
            return curvePointRepository.existsByCurveId(curvePoint.getCurveId());
        } else if (entity instanceof Rating rating) {
            return ratingRepository.existsByMoodysRating(rating.getMoodysRating());
        } else if (entity instanceof RuleName ruleName) {
            return ruleNameRepository.existsByName(ruleName.getName());
        } else if (entity instanceof Trade trade) {
            return tradeRepository.existsByAccount(trade.getAccount());
        } else if (entity instanceof User user) {
            return userRepository.existsByUsername(user.getUsername());
        }
        throw new IllegalArgumentException("Unknown entity type");
    }

    public Integer saveEntityAndReturnId(Object entity) {
        if (entity instanceof BidList b) {
            return bidListRepository.save(b).getId();
        } else if (entity instanceof CurvePoint c) {
            return curvePointRepository.save(c).getId();
        } else if (entity instanceof Rating r) {
            return ratingRepository.save(r).getId();
        } else if (entity instanceof RuleName rn) {
            return ruleNameRepository.save(rn).getId();
        } else if (entity instanceof Trade t) {
            return tradeRepository.save(t).getId();
        } else if (entity instanceof User u) {
            return userRepository.save(u).getId();
        }
        throw new IllegalArgumentException("Type d'entité non supporté: " + entity.getClass());
    }

    public Integer saveEntityWithDifferenceAndReturnId(Object entity) {
        if (entity instanceof BidList b) {
            b.setAccount("newUniqueAccount17118");
            return bidListRepository.save(b).getId();
        } else if (entity instanceof CurvePoint c) {
            c.setCurveId(1272);
            return curvePointRepository.save(c).getId();
        } else if (entity instanceof Rating r) {
            r.setMoodysRating("newUniqueMoodysRating17118");
            return ratingRepository.save(r).getId();
        } else if (entity instanceof RuleName rn) {
            rn.setName("newUniqueName17118");
            return ruleNameRepository.save(rn).getId();
        } else if (entity instanceof Trade t) {
            t.setAccount("newUniqueAccount17118");
            return tradeRepository.save(t).getId();
        } else if (entity instanceof User u) {
            u.setUsername("newUniqueUsername17118");
            return userRepository.save(u).getId();
        }
        throw new IllegalArgumentException("Type d'entité non supporté: " + entity.getClass());
    }
}
