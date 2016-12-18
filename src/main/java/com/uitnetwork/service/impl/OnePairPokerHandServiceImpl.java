package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.OnePairPokerHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ninhdoan on 12/17/16.
 */
@Service
public class OnePairPokerHandServiceImpl implements OnePairPokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestOnePairPokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      Set<Card> highestPair = cardService.getHighestPair(sevenCards);
      if (highestPair.isEmpty()) {
         return null;
      }

      Set<Card> remainingCardsAfterHighestPair = cardService.getAllCardsInSourceExcludeCards(sevenCards, highestPair);
      Set<Card> threeHighestCardsInRemaining = cardService.getHighestCards(remainingCardsAfterHighestPair, 3);


      Set<Card> bestOnePairPokerHand = Stream.concat(highestPair.stream(), threeHighestCardsInRemaining.stream())
            .collect(Collectors.toSet());
      return new PokerHand(PokerHandType.ONE_PAIR, bestOnePairPokerHand);
   }
}
