package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.FullHousePokerHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ninhdoan on 12/16/16.
 */

@Service
public class FullHousePokerHandServiceImpl implements FullHousePokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestFullHousePokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      Set<Card> highestThreeOfAKind = cardService.getHighestThreeOfAKind(sevenCards);
      if (highestThreeOfAKind.size() == 0) {
         return null;
      }

      Set<Card> remainingCards = cardService.getAllCardsInSourceExcludeCards(sevenCards, highestThreeOfAKind);
      Set<Card> highestPairInRemainingCards = cardService.getHighestPair(remainingCards);
      if (highestPairInRemainingCards.size() == 0) {
         return null;
      }

      Set<Card> bestFullHouse = Stream.concat(highestThreeOfAKind.stream(), highestPairInRemainingCards.stream())
            .collect(Collectors.toSet());

      return new PokerHand(PokerHandType.FULL_HOUSE, bestFullHouse);
   }
}
