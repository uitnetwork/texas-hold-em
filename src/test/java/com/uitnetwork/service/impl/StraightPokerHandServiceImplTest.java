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
 * Created by ninhdoan on 12/17/16.
 */
public class StraightPokerHandServiceImplTest extends AbstractMockitoTests {

   @InjectMocks
   private StraightPokerHandServiceImpl straightPokerHandService;

   @Mock
   private CardService cardService;


   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = straightPokerHandService.getBestStraightPokerHand(cards);

   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = straightPokerHandService.getBestStraightPokerHand(cards);

   }

   @Test
   public void returnNullWhenNotHave5ConsecutiveCards() {
      Set<Card> cards = cardsFromString("1H 2D 3D 4D 6D 7D 8D");
      when(cardService.getAtMostStraightCardsOrderByRank(cards)).thenReturn(cardsFromStringByOrder("1H 2D 3D 4H"));

      PokerHand pokerHand = straightPokerHandService.getBestStraightPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getAtMostStraightCardsOrderByRank(cards);
   }

   @Test
   public void returnBestStraight() {
      Set<Card> cards = cardsFromString("1H 2D 3D 4D 6D 7D 5S");
      List<Card> atMostStraightCardsOrderByRank = cardsFromStringByOrder("1H 2D 3D 4D 5S 6D 7D");
      when(cardService.getAtMostStraightCardsOrderByRank(cards)).thenReturn(atMostStraightCardsOrderByRank);

      PokerHand pokerHand = straightPokerHandService.getBestStraightPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.STRAIGHT);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("3D 4D 5S 6D 7D"));
      verify(cardService).getAtMostStraightCardsOrderByRank(cards);

   }

}