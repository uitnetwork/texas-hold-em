package com.uitnetwork.service.impl;

import com.uitnetwork.AbstractMockitoTests;
import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Set;

import static com.uitnetwork.CardUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ninhdoan on 12/16/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class FullHousePokerHandServiceImplTest extends AbstractMockitoTests {

   @Mock
   private CardService cardService;

   @InjectMocks
   private FullHousePokerHandServiceImpl fullHousePokerHandService;

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = fullHousePokerHandService.getBestFullHousePokerHand(cards);

   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = fullHousePokerHandService.getBestFullHousePokerHand(cards);

   }

   @Test
   public void returnNullWhenDoesNotHave3Kind() {
      Set<Card> cards = cardsFromString("1H 1S 2H 2S 3H 3S 4H");
      when(cardService.getHighestThreeOfAKind(cards)).thenReturn(Collections.emptySet());

      PokerHand pokerHand = fullHousePokerHandService.getBestFullHousePokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getHighestThreeOfAKind(cards);
   }

   @Test
   public void returnNullWhenHave3KindButNotAPair() {
      Set<Card> cards = cardsFromString("1H 1S 1C 2S 3H 5S 4H");
      Set<Card> highestThreeOfAKind = cardsFromString("1H 1S 1C");
      Set<Card> remainingCards = cardsFromString("2S 3H 5S 4H");
      when(cardService.getHighestThreeOfAKind(cards)).thenReturn(highestThreeOfAKind);
      when(cardService.getAllCardsInSourceExcludeCards(cards, highestThreeOfAKind)).thenReturn(remainingCards);
      when(cardService.getHighestPair(remainingCards)).thenReturn(Collections.emptySet());

      PokerHand pokerHand = fullHousePokerHandService.getBestFullHousePokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getHighestThreeOfAKind(cards);
      verify(cardService).getAllCardsInSourceExcludeCards(cards, highestThreeOfAKind);
      verify(cardService).getHighestPair(remainingCards);
   }

   @Test
   public void returnFullHouseWhenHave3KindAndAPair() {
      Set<Card> cards = cardsFromString("1H 1S 1C 2S 3H 5S 2H");
      Set<Card> highestThreeOfAKind = cardsFromString("1H 1S 1C");
      Set<Card> remainingCards = cardsFromString("2S 3H 5S 2H");
      Set<Card> highestPairInRemaining = cardsFromString("2S 2H");

      when(cardService.getHighestThreeOfAKind(cards)).thenReturn(highestThreeOfAKind);
      when(cardService.getAllCardsInSourceExcludeCards(cards, highestThreeOfAKind)).thenReturn(remainingCards);
      when(cardService.getHighestPair(remainingCards)).thenReturn(highestPairInRemaining);

      PokerHand pokerHand = fullHousePokerHandService.getBestFullHousePokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FULL_HOUSE);
      assertThat(pokerHand.getCards()).containsAll(highestThreeOfAKind);
      assertThat(pokerHand.getCards()).containsAll(highestPairInRemaining);
      verify(cardService).getHighestThreeOfAKind(cards);
      verify(cardService).getAllCardsInSourceExcludeCards(cards, highestThreeOfAKind);
      verify(cardService).getHighestPair(remainingCards);
   }

}