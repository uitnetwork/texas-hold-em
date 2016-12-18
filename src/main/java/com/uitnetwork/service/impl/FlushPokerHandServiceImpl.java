package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.CardRank;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.FlushPokerHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ninhdoan on 12/17/16.
 */
@Service
public class FlushPokerHandServiceImpl implements FlushPokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestFlushPokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      Set<Card> atMostSameTypeCards = cardService.getAtMostSameTypeCards(sevenCards);
      if (atMostSameTypeCards.size() < 5) {
         return null;
      }

      return getBestFlushFromFlushCards(atMostSameTypeCards);
   }

   private PokerHand getBestFlushFromFlushCards(Set<Card> flushCards) {
      if (flushCards.size() == 5) {
         return new PokerHand(PokerHandType.FLUSH, flushCards);
      }

      List<Card> orderByRankFlushCards = flushCards.stream()
            .sorted(Comparator.comparing(Card::getRank))
            .collect(Collectors.toList());

      Card firstCard = orderByRankFlushCards.get(0);
      if (firstCard.getRank() == CardRank.ACE) {
         return getBestFlushPokerHandWithAceAtFirst(orderByRankFlushCards);
      }

      return getBestFlushPokerHandWithoutAce(orderByRankFlushCards);

   }

   private PokerHand getBestFlushPokerHandWithAceAtFirst(List<Card> orderByRankFlushCards) {
      // special care for FLUSH with ACE in it by getting 4 last in ACE in the beginning
      int weakestCardInBestFlushWithAce = orderByRankFlushCards.size() - 4;
      List<Card> fourBestFlushCardsAfterAce = orderByRankFlushCards.subList(weakestCardInBestFlushWithAce, orderByRankFlushCards.size());

      Set<Card> bestFlushWithAce = Stream.concat(fourBestFlushCardsAfterAce.stream(), Collections.singleton(orderByRankFlushCards.get(0)).stream())
            .collect(Collectors.toSet());

      return new PokerHand(PokerHandType.FLUSH, new HashSet<>(bestFlushWithAce));
   }

   private PokerHand getBestFlushPokerHandWithoutAce(List<Card> orderByRankFlushCards) {
      int weakestCardInBestFlushWithoutAce = orderByRankFlushCards.size() - 5;
      List<Card> bestFlushCardsWithoutAce = orderByRankFlushCards.subList(weakestCardInBestFlushWithoutAce, orderByRankFlushCards.size());
      return new PokerHand(PokerHandType.FLUSH, new HashSet<>(bestFlushCardsWithoutAce));
   }
}
