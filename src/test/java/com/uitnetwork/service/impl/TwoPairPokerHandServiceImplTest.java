package com.uitnetwork.service.impl;

import com.uitnetwork.AbstractMockitoTests;
import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Set;

import static com.uitnetwork.CardUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ninhdoan on 12/17/16.
 */
public class TwoPairPokerHandServiceImplTest extends AbstractMockitoTests{

   @InjectMocks
   private TwoPairPokerHandServiceImpl twoPairPokerHandService;

   @Mock
   private CardService cardService;

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = twoPairPokerHandService.getBestTwoPairPokerHand(cards);

   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = twoPairPokerHandService.getBestTwoPairPokerHand(cards);
   }

   @Test
   public void returnNullWhenHaveNoPair() {
      Set<Card> cards = cardsFromString("1S 2S 4H 5H 6C 8D 9D");
      when(cardService.getHighestPair(cards)).thenReturn(Collections.emptySet());

      PokerHand pokerHand = twoPairPokerHandService.getBestTwoPairPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getHighestPair(cards);

   }

   @Test
   public void returnNullWhenHaveOnePair() {
      Set<Card> cards = cardsFromString("1S 1H 4H 5H 6C 8D 9D");
      Set<Card> highestPair=cardsFromString("1S 1H");
      Set<Card> remainingCardsAfterHighestPair = cardsFromString("4H 5H 6C 8D 9D");
      when(cardService.getHighestPair(cards)).thenReturn(highestPair);
      when(cardService.getAllCardsInSourceExcludeCards(cards, highestPair)).thenReturn(remainingCardsAfterHighestPair);
      when(cardService.getHighestPair(remainingCardsAfterHighestPair)).thenReturn(Collections.emptySet());

      PokerHand pokerHand = twoPairPokerHandService.getBestTwoPairPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getHighestPair(cards);
      verify(cardService).getAllCardsInSourceExcludeCards(cards, highestPair);
      verify(cardService).getHighestPair(remainingCardsAfterHighestPair);

   }

   @Test
   public void returnTwoPair() {
      Set<Card> cards = cardsFromString("1S 1H 4H 5H 4C 8D 9D");
      Set<Card> highestPair=cardsFromString("1S 1H");
      Set<Card> remainingCardsAfterHighestPair = cardsFromString("4H 5H 4C 8D 9D");
      Set<Card> highestPairInRemaining = cardsFromString("4H 4C");
      Set<Card> remainingCardsAfterTwoPair = cardsFromString("5H 8D 9D");
      Card highestCardAfterTwoPair = aCardFromString("9D");
      when(cardService.getHighestPair(cards)).thenReturn(highestPair);
      when(cardService.getAllCardsInSourceExcludeCards(cards, highestPair)).thenReturn(remainingCardsAfterHighestPair);
      when(cardService.getHighestPair(remainingCardsAfterHighestPair)).thenReturn(highestPairInRemaining);
      when(cardService.getAllCardsInSourceExcludeCards(remainingCardsAfterHighestPair, highestPairInRemaining)).thenReturn(remainingCardsAfterTwoPair);
      when(cardService.getHighestCard(remainingCardsAfterTwoPair)).thenReturn(highestCardAfterTwoPair);

      PokerHand pokerHand = twoPairPokerHandService.getBestTwoPairPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.TWO_PAIR);
      assertThat(pokerHand.getCards()).containsAll(highestPair);
      assertThat(pokerHand.getCards()).containsAll(highestPairInRemaining);
      assertThat(pokerHand.getCards()).contains(highestCardAfterTwoPair);
      verify(cardService).getHighestPair(cards);
      verify(cardService).getAllCardsInSourceExcludeCards(cards, highestPair);
      verify(cardService).getHighestPair(remainingCardsAfterHighestPair);
      verify(cardService).getAllCardsInSourceExcludeCards(remainingCardsAfterHighestPair, highestPairInRemaining);
      verify(cardService).getHighestCard(remainingCardsAfterTwoPair);
   }

}