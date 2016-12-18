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
public class HighCardPokerHandServiceImplTest extends AbstractMockitoTests {

   @InjectMocks
   private HighCardPokerHandServiceImpl highCardPokerHandService;

   @Mock
   private CardService cardService;

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasLessThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_6_CARDS);

      PokerHand pokerHand = highCardPokerHandService.getBestHighCardPokerHand(cards);
   }

   @Test(expected = IllegalArgumentException.class)
   public void throwExceptionWhenInputHasMoreThan7Cards() {
      Set<Card> cards = cardsFromString(STRING_WITH_8_CARDS);

      PokerHand pokerHand = highCardPokerHandService.getBestHighCardPokerHand(cards);
   }

   @Test
   public void returnBestHighCard() {
      Set<Card> cards = cardsFromString("1S 10S 11D 13D 9D 2S 3S");
      Set<Card> fiveHighestCards = cardsFromString("1S 10S 11D 13D 9D");
      when(cardService.getHighestCards(cards, 5)).thenReturn(fiveHighestCards);

      PokerHand pokerHand = highCardPokerHandService.getBestHighCardPokerHand(cards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.HIGH_CARD);
      assertThat(pokerHand.getCards()).containsAll(fiveHighestCards);
      verify(cardService).getHighestCards(cards, 5);
   }

}