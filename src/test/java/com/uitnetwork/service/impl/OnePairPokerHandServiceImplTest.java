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
public class OnePairPokerHandServiceImplTest extends AbstractMockitoTests {

   @InjectMocks
   private OnePairPokerHandServiceImpl onePairPokerHandService;

   @Mock
   private CardService cardService;

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = onePairPokerHandService.getBestOnePairPokerHand(cards);

   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = onePairPokerHandService.getBestOnePairPokerHand(cards);

   }

   @Test
   public void returnNullWhenHaveNoPair() {
      Set<Card> cards = cardsFromString("1S 2S 3D 5D 9H 10H 11H");
      when(cardService.getHighestPair(cards)).thenReturn(Collections.emptySet());

      PokerHand pokerHand = onePairPokerHandService.getBestOnePairPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getHighestPair(cards);
   }

   @Test
   public void returnBestOnePair() {
      Set<Card> cards = cardsFromString("1S 1D 3D 5D 9H 10H 11H");
      Set<Card> highestPair = cardsFromString("1S 1D");
      Set<Card> remainingCards = cardsFromString("3D 5D 9H 10H 11H");
      Set<Card> threeHighestCardsInRemaining = cardsFromString("9H 10H 11H");
      when(cardService.getHighestPair(cards)).thenReturn(highestPair);
      when(cardService.getAllCardsInSourceExcludeCards(cards, highestPair)).thenReturn(remainingCards);
      when(cardService.getHighestCards(remainingCards, 3)).thenReturn(threeHighestCardsInRemaining);

      PokerHand pokerHand = onePairPokerHandService.getBestOnePairPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.ONE_PAIR);
      assertThat(pokerHand.getCards()).containsAll(highestPair);
      assertThat(pokerHand.getCards()).containsAll(threeHighestCardsInRemaining);
      verify(cardService).getHighestPair(cards);
      verify(cardService).getAllCardsInSourceExcludeCards(cards, highestPair);
      verify(cardService).getHighestCards(remainingCards, 3);
   }

}