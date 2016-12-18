package com.uitnetwork.domain;

import org.junit.Test;

import java.util.Collections;

import static com.uitnetwork.CardUtils.aCardFromString;
import static com.uitnetwork.CardUtils.cardsFromString;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ninhdoan on 12/15/16.
 */
public class PokerHandTest {

   @Test(expected = IllegalArgumentException.class)
   public void canNotCreatePokerHandWithoutType() {
      new PokerHand(null, Collections.emptySet());
   }

   @Test(expected = IllegalArgumentException.class)
   public void canNotCreatePokerHandWhenCardsEmpty() {
      new PokerHand(PokerHandType.FLUSH, Collections.emptySet());
   }

   @Test(expected = IllegalArgumentException.class)
   public void canNotCreatePokerHandWhenCardsHasLessThan5Items() {
      new PokerHand(PokerHandType.FLUSH, cardsFromString("1H 2H 3H 4H"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void canNotCreatePokerHandWhenCardsHasMoreThan5Items() {
      new PokerHand(PokerHandType.FLUSH, cardsFromString("1H 2H 3H 4H 5H 6H"));
   }

   @Test
   public void canCreatePokerHandWhenCardsHas5Items() {
      PokerHand pokerHand = new PokerHand(PokerHandType.FLUSH, cardsFromString("1H 2H 3H 4H 5H"));

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FLUSH);
      assertThat(pokerHand.getCards()).contains(aCardFromString("1H"));
      assertThat(pokerHand.getCards()).contains(aCardFromString("2H"));
      assertThat(pokerHand.getCards()).contains(aCardFromString("3H"));
      assertThat(pokerHand.getCards()).contains(aCardFromString("4H"));
      assertThat(pokerHand.getCards()).contains(aCardFromString("5H"));
   }

}