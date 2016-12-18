package com.uitnetwork.service.impl;

import com.uitnetwork.AbstractMockitoTests;
import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.CardService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Set;

import static com.uitnetwork.CardUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ninhdoan on 12/15/16.
 */
public class StraightFlushPokerHandServiceImplTest extends AbstractMockitoTests {

   @InjectMocks
   private StraightFlushPokerHandServiceImpl straightFlushPokerHandService;

   @Mock
   private CardService cardService;

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = straightFlushPokerHandService.getBestStraightFlushPokerHand(cards);

   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = straightFlushPokerHandService.getBestStraightFlushPokerHand(cards);

   }

   @Test
   public void returnNullWhenNotFlush() {
      Set<Card> cards = cardsFromString("1H 2H 3H 4H 5S 6S 7S");
      Set<Card> atMostSameTypeCards = cardsFromString("1H 2H 3H 4H");
      when(cardService.getAtMostSameTypeCards(cards)).thenReturn(atMostSameTypeCards);

      PokerHand pokerHand = straightFlushPokerHandService.getBestStraightFlushPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getAtMostSameTypeCards(cards);
   }

   @Test
   public void returnNullWhenFlushButNotStraight() {
      Set<Card> cards = cardsFromString("1H 2H 3H 4H 6H 7H 8H");
      Set<Card> atMostSameTypeCards = cardsFromString("1H 2H 3H 4H 6H 7H 8H");
      List<Card> atMostStraightCards = cardsFromStringByOrder("1H 2H 3H 4H");
      when(cardService.getAtMostSameTypeCards(cards)).thenReturn(atMostSameTypeCards);
      when(cardService.getAtMostStraightCardsOrderByRank(atMostSameTypeCards)).thenReturn(atMostStraightCards);

      PokerHand pokerHand = straightFlushPokerHandService.getBestStraightFlushPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getAtMostSameTypeCards(cards);
      verify(cardService).getAtMostStraightCardsOrderByRank(atMostSameTypeCards);
   }

   @Test
   public void returnStraightFlush() {
      Set<Card> cards = cardsFromString("1H 2H 3H 4H 5H 6H 7S");

      Set<Card> atMostSameTypeCards = cardsFromString("1H 2H 3H 4H 5H 6H");
      List<Card> atMostStraightCardsFromAtMostSameTypeCards = cardsFromStringByOrder("1H 2H 3H 4H 5H 6H");
      when(cardService.getAtMostSameTypeCards(cards)).thenReturn(atMostSameTypeCards);
      when(cardService.getAtMostStraightCardsOrderByRank(atMostSameTypeCards)).thenReturn(atMostStraightCardsFromAtMostSameTypeCards);

      PokerHand pokerHand = straightFlushPokerHandService.getBestStraightFlushPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.STRAIGHT_FLUSH);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 3H 4H 5H 6H"));
      verify(cardService).getAtMostSameTypeCards(cards);
      verify(cardService).getAtMostStraightCardsOrderByRank(atMostSameTypeCards);
   }
}