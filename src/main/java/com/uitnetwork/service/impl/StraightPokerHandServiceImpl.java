package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.StraightPokerHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ninhdoan on 12/17/16.
 */
@Service
public class StraightPokerHandServiceImpl implements StraightPokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestStraightPokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      List<Card> atMostStraightCardsOrderByRank = cardService.getAtMostStraightCardsOrderByRank(sevenCards);
      if (atMostStraightCardsOrderByRank.size() < 5) {
         return null;
      }

      int weakestCardInBestStraight = atMostStraightCardsOrderByRank.size() - 5;

      List<Card> bestStraight = atMostStraightCardsOrderByRank.subList(weakestCardInBestStraight, atMostStraightCardsOrderByRank.size());

      return new PokerHand(PokerHandType.STRAIGHT, new HashSet<>(bestStraight));
   }
}
