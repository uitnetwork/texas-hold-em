package com.uitnetwork.service.impl;

import com.uitnetwork.AbstractMockitoTests;
import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static com.uitnetwork.CardUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ninhdoan on 12/15/16.
 */
public class FourOfAKindPokerHandServiceImplTest extends AbstractMockitoTests {

   @InjectMocks
   private FourOfAKindPokerHandServiceImpl fourOfAKindPokerHandService;

   @Mock
   private CardService cardService;

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = fourOfAKindPokerHandService.getBestFourOfAKindPokerHand(cards);

   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = fourOfAKindPokerHandService.getBestFourOfAKindPokerHand(cards);

   }

   @Test
   public void returnNullWhenDoesNotHave4Kind() {
      Set<Card> cards = cardsFromString("1H 1S 1D 4H 6H 7H 8H");
      Set<Card> atMostSameTypeCards = cardsFromString("1H 1S 1D");
      when(cardService.getAtMostSameRankCards(cards)).thenReturn(atMostSameTypeCards);

      PokerHand pokerHand = fourOfAKindPokerHandService.getBestFourOfAKindPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getAtMostSameRankCards(cards);
   }

   @Test
   public void returnFourOfAKindWhenHave4Kind() {
      Set<Card> cards = cardsFromString("1H 1S 1D 1C 6H 7H 8H");
      Set<Card> fourOfAKindCards = cardsFromString("1H 1S 1D 1C");
      Set<Card> remainingCards = cardsFromString("6H 7H 8H");
      Card highestCardInRemaining = aCardFromString("8H");
      when(cardService.getAtMostSameRankCards(cards)).thenReturn(fourOfAKindCards);
      when(cardService.getAllCardsInSourceExcludeCards(cards, fourOfAKindCards)).thenReturn(remainingCards);
      when(cardService.getHighestCard(remainingCards)).thenReturn(highestCardInRemaining);

      PokerHand pokerHand = fourOfAKindPokerHandService.getBestFourOfAKindPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FOUR_OF_A_KIND);
      assertThat(pokerHand.getCards()).hasSize(5);
      assertThat(pokerHand.getCards()).containsAll(fourOfAKindCards);
      assertThat(pokerHand.getCards()).contains(highestCardInRemaining);
      verify(cardService).getAtMostSameRankCards(cards);
      verify(cardService).getAllCardsInSourceExcludeCards(cards, fourOfAKindCards);
      verify(cardService).getHighestCard(remainingCards);
   }
}