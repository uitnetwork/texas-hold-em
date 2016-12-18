package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.ThreeOfAKindPokerHandService;
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
public class ThreeOfAKindPokerHandServiceImpl implements ThreeOfAKindPokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestThreeOfAKindPokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      Set<Card> highestThreeOfAKind = cardService.getHighestThreeOfAKind(sevenCards);
      if (highestThreeOfAKind.size() == 0) {
         return null;
      }

      Set<Card> remainingCards = cardService.getAllCardsInSourceExcludeCards(sevenCards, highestThreeOfAKind);
      Set<Card> twoHighestCardsInRemaining = cardService.getHighestCards(remainingCards, 2);

      Set<Card> bestThreeOfAKindPokerHand = Stream.concat(highestThreeOfAKind.stream(), twoHighestCardsInRemaining.stream())
            .collect(Collectors.toSet());

      return new PokerHand(PokerHandType.THREE_OF_A_KIND, bestThreeOfAKindPokerHand);
   }
}
