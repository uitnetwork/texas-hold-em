package com.uitnetwork.service.impl;

import com.uitnetwork.AbstractMockitoTests;
import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.*;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static com.uitnetwork.CardUtils.cardsFromString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by ninhdoan on 12/17/16.
 */
public class PokerHandServiceImplTest extends AbstractMockitoTests {

   // NOTE: JUST A TEST INPUT
   private static Set<Card> SEVEN_CARDS_AS_INPUT = cardsFromString("1H 2H 3H 4H 5H 6H 7H");

   @InjectMocks
   private PokerHandServiceImpl pokerHandService;

   @Mock
   private StraightFlushPokerHandService straightFlushPokerHandService;

   @Mock
   private FourOfAKindPokerHandService fourOfAKindPokerHandService;

   @Mock
   private FullHousePokerHandService fullHousePokerHandService;

   @Mock
   private FlushPokerHandService flushPokerHandService;

   @Mock
   private StraightPokerHandService straightPokerHandService;

   @Mock
   private ThreeOfAKindPokerHandService threeOfAKindPokerHandService;

   @Mock
   private TwoPairPokerHandService twoPairPokerHandService;

   @Mock
   private OnePairPokerHandService onePairPokerHandService;

   @Mock
   private HighCardPokerHandService highCardPokerHandService;


   @Test
   public void returnStraightFlushPokerHand() {
      PokerHand straightFlushPokerHand = createTestPokerHandWithType(PokerHandType.STRAIGHT_FLUSH);
      when(straightFlushPokerHandService.getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(straightFlushPokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(straightFlushPokerHand);
      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verifyNoInteractionWithFourOfAKindPokerHandService();
      verifyNoInteractionWithFullHousePokerHandService();
      verifyNoInteractionWithFlushPokerHandService();
      verifyNoInteractionWithStraightPokerHandService();
      verifyNoInteractionWithThreeOfAKindPokerHandService();
      verifyNoInteractionWithTwoPairPokerHandService();
      verifyNoInteractionWithOnePairPokerHandService();
      verifyNoInteractionWithHighCardPokerHandService();
   }

   @Test
   public void returnFourOfAKindPokerHand() {
      PokerHand fourOfAKindPokerHand = createTestPokerHandWithType(PokerHandType.FOUR_OF_A_KIND);
      when(fourOfAKindPokerHandService.getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(fourOfAKindPokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(fourOfAKindPokerHand);
      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fourOfAKindPokerHandService).getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verifyNoInteractionWithFullHousePokerHandService();
      verifyNoInteractionWithFlushPokerHandService();
      verifyNoInteractionWithStraightPokerHandService();
      verifyNoInteractionWithThreeOfAKindPokerHandService();
      verifyNoInteractionWithTwoPairPokerHandService();
      verifyNoInteractionWithOnePairPokerHandService();
      verifyNoInteractionWithHighCardPokerHandService();

   }

   @Test
   public void returnFullHousePokerHand() {
      PokerHand fullHousePokerHand = createTestPokerHandWithType(PokerHandType.FULL_HOUSE);
      when(fullHousePokerHandService.getBestFullHousePokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(fullHousePokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(fullHousePokerHand);
      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fourOfAKindPokerHandService).getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fullHousePokerHandService).getBestFullHousePokerHand(SEVEN_CARDS_AS_INPUT);
      verifyNoInteractionWithFlushPokerHandService();
      verifyNoInteractionWithStraightPokerHandService();
      verifyNoInteractionWithThreeOfAKindPokerHandService();
      verifyNoInteractionWithTwoPairPokerHandService();
      verifyNoInteractionWithOnePairPokerHandService();
      verifyNoInteractionWithHighCardPokerHandService();

   }

   @Test
   public void returnFlushPokerHand() {
      PokerHand flushPokerHand = createTestPokerHandWithType(PokerHandType.FLUSH);
      when(flushPokerHandService.getBestFlushPokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(flushPokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(flushPokerHand);

      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fourOfAKindPokerHandService).getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fullHousePokerHandService).getBestFullHousePokerHand(SEVEN_CARDS_AS_INPUT);
      verify(flushPokerHandService).getBestFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verifyNoInteractionWithStraightPokerHandService();
      verifyNoInteractionWithThreeOfAKindPokerHandService();
      verifyNoInteractionWithTwoPairPokerHandService();
      verifyNoInteractionWithOnePairPokerHandService();
      verifyNoInteractionWithHighCardPokerHandService();

   }

   @Test
   public void returnStraightPokerHand() {
      PokerHand straightPokerHand = createTestPokerHandWithType(PokerHandType.STRAIGHT);
      when(straightPokerHandService.getBestStraightPokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(straightPokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(straightPokerHand);
      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fourOfAKindPokerHandService).getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fullHousePokerHandService).getBestFullHousePokerHand(SEVEN_CARDS_AS_INPUT);
      verify(flushPokerHandService).getBestFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(straightPokerHandService).getBestStraightPokerHand(SEVEN_CARDS_AS_INPUT);
      verifyNoInteractionWithThreeOfAKindPokerHandService();
      verifyNoInteractionWithTwoPairPokerHandService();
      verifyNoInteractionWithOnePairPokerHandService();
      verifyNoInteractionWithHighCardPokerHandService();

   }

   @Test
   public void returnThreeOfAKindPokerHand() {
      PokerHand threeOfAKindPokerHand = createTestPokerHandWithType(PokerHandType.THREE_OF_A_KIND);
      when(threeOfAKindPokerHandService.getBestThreeOfAKindPokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(threeOfAKindPokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(threeOfAKindPokerHand);
      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fourOfAKindPokerHandService).getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fullHousePokerHandService).getBestFullHousePokerHand(SEVEN_CARDS_AS_INPUT);
      verify(flushPokerHandService).getBestFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(straightPokerHandService).getBestStraightPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(threeOfAKindPokerHandService).getBestThreeOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verifyNoInteractionWithTwoPairPokerHandService();
      verifyNoInteractionWithOnePairPokerHandService();
      verifyNoInteractionWithHighCardPokerHandService();

   }

   @Test
   public void returnTwoPairsPokerHand() {
      PokerHand twoPairsPokerHand = createTestPokerHandWithType(PokerHandType.TWO_PAIR);
      when(twoPairPokerHandService.getBestTwoPairPokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(twoPairsPokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(twoPairsPokerHand);
      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fourOfAKindPokerHandService).getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fullHousePokerHandService).getBestFullHousePokerHand(SEVEN_CARDS_AS_INPUT);
      verify(flushPokerHandService).getBestFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(straightPokerHandService).getBestStraightPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(threeOfAKindPokerHandService).getBestThreeOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(twoPairPokerHandService).getBestTwoPairPokerHand(SEVEN_CARDS_AS_INPUT);
      verifyNoInteractionWithOnePairPokerHandService();
      verifyNoInteractionWithHighCardPokerHandService();

   }

   @Test
   public void returnOnePairPokerHand() {
      PokerHand onePairPokerHand = createTestPokerHandWithType(PokerHandType.ONE_PAIR);
      when(onePairPokerHandService.getBestOnePairPokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(onePairPokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(onePairPokerHand);
      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fourOfAKindPokerHandService).getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fullHousePokerHandService).getBestFullHousePokerHand(SEVEN_CARDS_AS_INPUT);
      verify(flushPokerHandService).getBestFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(straightPokerHandService).getBestStraightPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(threeOfAKindPokerHandService).getBestThreeOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(twoPairPokerHandService).getBestTwoPairPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(onePairPokerHandService).getBestOnePairPokerHand(SEVEN_CARDS_AS_INPUT);
      verifyNoInteractionWithHighCardPokerHandService();
   }

   @Test
   public void returnHighCardPokerHand() {
      PokerHand highCardPokerHand = createTestPokerHandWithType(PokerHandType.HIGH_CARD);
      when(highCardPokerHandService.getBestHighCardPokerHand(SEVEN_CARDS_AS_INPUT)).thenReturn(highCardPokerHand);

      PokerHand pokerHand = pokerHandService.getBestPokerHand(SEVEN_CARDS_AS_INPUT);

      assertThat(pokerHand).isEqualTo(highCardPokerHand);
      verify(straightFlushPokerHandService).getBestStraightFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fourOfAKindPokerHandService).getBestFourOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(fullHousePokerHandService).getBestFullHousePokerHand(SEVEN_CARDS_AS_INPUT);
      verify(flushPokerHandService).getBestFlushPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(straightPokerHandService).getBestStraightPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(threeOfAKindPokerHandService).getBestThreeOfAKindPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(twoPairPokerHandService).getBestTwoPairPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(onePairPokerHandService).getBestOnePairPokerHand(SEVEN_CARDS_AS_INPUT);
      verify(highCardPokerHandService).getBestHighCardPokerHand(SEVEN_CARDS_AS_INPUT);
   }


   // NOTE: JUST A SAMPLE PokerHand
   private PokerHand createTestPokerHandWithType(PokerHandType pokerHandType) {
      PokerHand pokerHand = new PokerHand(pokerHandType, cardsFromString("1H 1S 1C 1D 7H"));
      return pokerHand;
   }

   private void verifyNoInteractionWithFourOfAKindPokerHandService() {
      verify(fourOfAKindPokerHandService, never()).getBestFourOfAKindPokerHand(anyObject());
   }

   private void verifyNoInteractionWithFullHousePokerHandService() {
      verify(fullHousePokerHandService, never()).getBestFullHousePokerHand(anyObject());
   }

   private void verifyNoInteractionWithFlushPokerHandService() {
      verify(flushPokerHandService, never()).getBestFlushPokerHand(anyObject());
   }

   private void verifyNoInteractionWithStraightPokerHandService() {
      verify(straightPokerHandService, never()).getBestStraightPokerHand(anyObject());
   }

   private void verifyNoInteractionWithThreeOfAKindPokerHandService() {
      verify(threeOfAKindPokerHandService, never()).getBestThreeOfAKindPokerHand(anyObject());
   }


   private void verifyNoInteractionWithTwoPairPokerHandService() {
      verify(twoPairPokerHandService, never()).getBestTwoPairPokerHand(anyObject());
   }


   private void verifyNoInteractionWithOnePairPokerHandService() {
      verify(onePairPokerHandService, never()).getBestOnePairPokerHand(anyObject());
   }

   private void verifyNoInteractionWithHighCardPokerHandService() {
      verify(highCardPokerHandService, never()).getBestHighCardPokerHand(anyObject());
   }

}