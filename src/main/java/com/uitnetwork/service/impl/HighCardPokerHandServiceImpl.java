package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import com.uitnetwork.service.HighCardPokerHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * Created by ninhdoan on 12/17/16.
 */
@Service
public class HighCardPokerHandServiceImpl implements HighCardPokerHandService {

   @Autowired
   private CardService cardService;

   @Override
   public PokerHand getBestHighCardPokerHand(Set<Card> sevenCards) {
      Assert.isTrue(sevenCards.size() == 7, "cards should contain 7 items");

      Set<Card> fiveHighestCards = cardService.getHighestCards(sevenCards, 5);
      return new PokerHand(PokerHandType.HIGH_CARD, fiveHighestCards);
   }
}
