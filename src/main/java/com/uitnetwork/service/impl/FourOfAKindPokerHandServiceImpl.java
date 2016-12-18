package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.FourOfAKindPokerHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ninhdoan on 12/15/16.
 */
@Service
public class FourOfAKindPokerHandServiceImpl implements FourOfAKindPokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestFourOfAKindPokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      Set<Card> fourOfAKindCards = cardService.getAtMostSameRankCards(sevenCards);
      if (fourOfAKindCards.size() < 4) {
         return null;
      }

      Set<Card> remainingCards = cardService.getAllCardsInSourceExcludeCards(sevenCards, fourOfAKindCards);

      Card highestCardInRemaining = cardService.getHighestCard(remainingCards);

      Set<Card> bestFourOfAKindPokerHand = Stream.concat(fourOfAKindCards.stream(), Collections.singleton(highestCardInRemaining).stream()).collect(Collectors.toSet());
      return new PokerHand(PokerHandType.FOUR_OF_A_KIND, bestFourOfAKindPokerHand);
   }

}
