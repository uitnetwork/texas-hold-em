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
public class ThreeOfAKindPokerHandServiceImplTest extends AbstractMockitoTests {

   @InjectMocks
   private ThreeOfAKindPokerHandServiceImpl threeOfAKindPokerHandService;

   @Mock
   private CardService cardService;

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = threeOfAKindPokerHandService.getBestThreeOfAKindPokerHand(cards);

   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = threeOfAKindPokerHandService.getBestThreeOfAKindPokerHand(cards);

   }

   @Test
   public void returnNullWhenNotHaveThreeOfAKind() {
      Set<Card> cards = cardsFromString("1S 1D 2D 2S 3D 3S 4D");
      when(cardService.getHighestThreeOfAKind(cards)).thenReturn(Collections.emptySet());

      PokerHand pokerHand = threeOfAKindPokerHandService.getBestThreeOfAKindPokerHand(cards);

      assertThat(pokerHand).isNull();
      verify(cardService).getHighestThreeOfAKind(cards);

   }

   @Test
   public void returnThreeOfAKind() {
      Set<Card> cards = cardsFromString("1S 1D 1C 2S 3D 5S 4D");
      Set<Card> threeOfAKind = cardsFromString("1S 1D 1C");
      Set<Card> remainingCards = cardsFromString("2S 3D 5S 4D");
      Set<Card> twoHighestCardsInRemaining = cardsFromString("5S 4D");
      when(cardService.getHighestThreeOfAKind(cards)).thenReturn(threeOfAKind);
      when(cardService.getAllCardsInSourceExcludeCards(cards, threeOfAKind)).thenReturn(remainingCards);
      when(cardService.getHighestCards(remainingCards, 2)).thenReturn(twoHighestCardsInRemaining);


      PokerHand pokerHand = threeOfAKindPokerHandService.getBestThreeOfAKindPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.THREE_OF_A_KIND);
      assertThat(pokerHand.getCards()).containsAll(threeOfAKind);
      assertThat(pokerHand.getCards()).containsAll(twoHighestCardsInRemaining);
      verify(cardService).getHighestThreeOfAKind(cards);
      verify(cardService).getAllCardsInSourceExcludeCards(cards, threeOfAKind);
      verify(cardService).getHighestCards(remainingCards, 2);
   }

}