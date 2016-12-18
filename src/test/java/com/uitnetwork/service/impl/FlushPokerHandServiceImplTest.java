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
 * Created by ninhdoan on 12/17/16.
 */
public class FlushPokerHandServiceImplTest extends AbstractMockitoTests {

   @InjectMocks
   private FlushPokerHandServiceImpl flushPokerHandService;

   @Mock
   private CardService cardService;

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = flushPokerHandService.getBestFlushPokerHand(cards);

   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = flushPokerHandService.getBestFlushPokerHand(cards);

   }

   @Test
   public void returnNullWhenNotHave5SameTypeCards() {
      Set<Card> cards = cardsFromString("1H 3H 4H 7H 8D 10D 11D");
      when(cardService.getAtMostSameTypeCards(cards)).thenReturn(cardsFromString("1H 3H 4H 7H"));

      PokerHand pokerHand = flushPokerHandService.getBestFlushPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getAtMostSameTypeCards(cards);
   }

   @Test
   public void returnFlushWhenThereAreOnly5SameTypeCards() {
      Set<Card> cards = cardsFromString("1H 3H 4H 7H 8D 10H 11D");
      Set<Card> atMostSameTypeCards = cardsFromString("1H 3H 4H 7H 10H");
      when(cardService.getAtMostSameTypeCards(cards)).thenReturn(atMostSameTypeCards);

      PokerHand pokerHand = flushPokerHandService.getBestFlushPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FLUSH);
      assertThat(pokerHand.getCards()).containsAll(atMostSameTypeCards);
      verify(cardService).getAtMostSameTypeCards(cards);
   }

   @Test
   public void returnBestFlushWhenThereAreMoreThan5SameTypeCardsWithoutAce() {
      Set<Card> cards = cardsFromString("2H 3H 4H 7H 8H 10H 11H");
      Set<Card> atMostSameTypeCards = cardsFromString("2H 3H 4H 7H 8H 10H 11H");
      when(cardService.getAtMostSameTypeCards(cards)).thenReturn(atMostSameTypeCards);

      PokerHand pokerHand = flushPokerHandService.getBestFlushPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FLUSH);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("4H 7H 8H 10H 11H"));
      verify(cardService).getAtMostSameTypeCards(cards);
   }

   @Test
   public void returnBestFlushWhenThereAreMoreThan5SameTypeCardsWithAce() {
      Set<Card> cards = cardsFromString("1H 3H 4H 7H 8H 10H 11H");
      Set<Card> atMostSameTypeCards = cardsFromString("1H 3H 4H 7H 8H 10H 11H");
      when(cardService.getAtMostSameTypeCards(cards)).thenReturn(atMostSameTypeCards);

      PokerHand pokerHand = flushPokerHandService.getBestFlushPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FLUSH);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("1H 7H 8H 10H 11H"));
      verify(cardService).getAtMostSameTypeCards(cards);
   }

}