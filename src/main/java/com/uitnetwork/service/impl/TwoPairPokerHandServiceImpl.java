package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.TwoPairPokerHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ninhdoan on 12/17/16.
 */
@Service
public class TwoPairPokerHandServiceImpl implements TwoPairPokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestTwoPairPokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      Set<Card> highestPair = cardService.getHighestPair(sevenCards);
      if (highestPair.isEmpty()) {
         return null;
      }

      Set<Card> remainingCardsAfterHighestPair = cardService.getAllCardsInSourceExcludeCards(sevenCards, highestPair);
      Set<Card> highestPairInRemaining = cardService.getHighestPair(remainingCardsAfterHighestPair);
      if (highestPairInRemaining.isEmpty()) {
         return null;
      }

      Set<Card> remainingCardsAfterTwoPairs = cardService.getAllCardsInSourceExcludeCards(remainingCardsAfterHighestPair, highestPairInRemaining);
      Card highestCardInRemaining = cardService.getHighestCard(remainingCardsAfterTwoPairs);

      Set<Card> bestTwoPair = Stream.of(highestPair.stream(), highestPairInRemaining.stream(), Stream.of(highestCardInRemaining))
            .flatMap(Function.identity())
            .collect(Collectors.toSet());

      return new PokerHand(PokerHandType.TWO_PAIR, bestTwoPair);
   }
}
