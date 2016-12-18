package com.uitnetwork.domain;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * Created by ninhdoan on 12/15/16.
 */
@Getter
public class PokerHand {

   private PokerHandType pokerHandType;

   private Set<Card> cards;


   public PokerHand(PokerHandType pokerHandType, Set<Card> cards) {
      Assert.notNull(pokerHandType, "pokerHandType should not be null");
      Assert.isTrue(cards.size() == 5, "cards should contain 5 items");

      this.pokerHandType = pokerHandType;
      this.cards = cards;
   }
}
