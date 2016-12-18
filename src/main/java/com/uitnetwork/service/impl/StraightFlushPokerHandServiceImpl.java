package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.StraightFlushPokerHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ninhdoan on 12/15/16.
 */
@Service
public class StraightFlushPokerHandServiceImpl implements StraightFlushPokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestStraightFlushPokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      Set<Card> atMostSameTypeCards = cardService.getAtMostSameTypeCards(sevenCards);
      if (atMostSameTypeCards.size() < 5) {
         return null;
      }

      List<Card> atMostStraightCardsFromAtMostSameTypeCards = cardService.getAtMostStraightCardsOrderByRank(atMostSameTypeCards);
      if (atMostStraightCardsFromAtMostSameTypeCards.size() < 5) {
         return null;
      }

      int weakestCardInBestStraightFlush = atMostStraightCardsFromAtMostSameTypeCards.size() - 5;

      List<Card> bestStraightFlush = atMostStraightCardsFromAtMostSameTypeCards.subList(weakestCardInBestStraightFlush, atMostStraightCardsFromAtMostSameTypeCards.size());

      return new PokerHand(PokerHandType.STRAIGHT_FLUSH, new HashSet<>(bestStraightFlush));

   }
}
