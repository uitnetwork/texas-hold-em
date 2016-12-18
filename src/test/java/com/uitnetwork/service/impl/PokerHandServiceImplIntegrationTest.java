package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.CardRank;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.domain.PokerHandType;
import com.uitnetwork.service.PokerHandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static com.uitnetwork.CardUtils.cardsFromString;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ninhdoan on 12/18/16.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PokerHandServiceImplIntegrationTest {

   @Autowired
   private PokerHandService pokerHandService;

   @Test
   public void straightFlushInNormal() {
      Set<Card> sevenCards = cardsFromString("1H 2H 3H 4H 5H 6H 7H");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.STRAIGHT_FLUSH);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("3H 4H 5H 6H 7H"));
   }

   @Test
   public void straightFlushWithAceLast() {
      Set<Card> sevenCards = cardsFromString("1H 9H 10H 11H 12H 13H 5H");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.STRAIGHT_FLUSH);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("1H 10H 11H 12H 13H"));
   }

   @Test
   public void fourOfAKindWithNormal5thCard() {
      Set<Card> sevenCards = cardsFromString("2H 2S 2D 2C 3S 5S 7S");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FOUR_OF_A_KIND);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 2S 2D 2C 7S"));
   }

   @Test
   public void fourOfAKindWithAae5thCard() {
      Set<Card> sevenCards = cardsFromString("2H 2S 2D 2C 3S 1S 7S");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FOUR_OF_A_KIND);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 2S 2D 2C 1S"));
   }

   @Test
   public void fullHouseWhenOneThreeKindAndOnePair() {
      Set<Card> sevenCards = cardsFromString("2H 2S 5D 3H 3S 3D 7S");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FULL_HOUSE);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("3H 3S 3D 2H 2S"));
   }

   @Test
   public void fullHouseWhenOneThreeKindAndTwoNormalPair() {
      Set<Card> sevenCards = cardsFromString("2H 2S 7D 3H 3S 3D 7S");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FULL_HOUSE);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("3H 3S 3D 7D 7S"));
   }

   @Test
   public void fullHouseWhenOneThreeKindAndTwoPairWithAce() {
      Set<Card> sevenCards = cardsFromString("1H 1S 7D 3H 3S 3D 7S");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FULL_HOUSE);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("3H 3S 3D 1H 1S"));
   }


   @Test
   public void fullHouseWhenDoubleThreeNormalKind() {
      Set<Card> sevenCards = cardsFromString("10H 10S 10D 3H 3S 3D 7S");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FULL_HOUSE);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("10H 10S 10D"));
      verifyCardsContainANumberOfRank(pokerHand.getCards(), CardRank.THREE, 2);
   }

   private void verifyCardsContainANumberOfRank(Set<Card> cards, CardRank rank, int expectedNumberOfCards) {
      long numberOfCardsWithRank = cards.stream().filter(card -> card.getRank() == rank).count();
      assertThat(numberOfCardsWithRank).as("Expect " + expectedNumberOfCards + " cards with rank: " + rank + " in: " + cards + ". But got " + numberOfCardsWithRank + " cards").isEqualTo(expectedNumberOfCards);
   }

   @Test
   public void fullHouseWhenDoubleThreeKindWithAce() {
      Set<Card> sevenCards = cardsFromString("10H 10S 10D 1H 1S 1D 7S");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FULL_HOUSE);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("1H 1S 1D"));
      verifyCardsContainANumberOfRank(pokerHand.getCards(), CardRank.TEN, 2);
   }

   @Test
   public void flushWithoutAce() {
      Set<Card> sevenCards = cardsFromString("10H 9H 5H 4H 3H 1D 12H");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FLUSH);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("12H 10H 9H 5H 4H"));
   }

   @Test
   public void flushWithAce() {
      Set<Card> sevenCards = cardsFromString("10H 9H 5H 4H 1H 1D 12H");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.FLUSH);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("12H 10H 9H 5H 1H"));
   }


   @Test
   public void straightInNormal() {
      Set<Card> sevenCards = cardsFromString("1H 9S 5H 4H 3D 6D 2H");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.STRAIGHT);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("5H 4H 3D 6D 2H"));
   }

   @Test
   public void straightWithAce() {
      Set<Card> sevenCards = cardsFromString("1H 9S 10H 11H 12D 13D 2H");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.STRAIGHT);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("10H 11H 12D 13D 1H"));
   }

   @Test
   public void threeNormalKindWithoutAceInRemaining() {
      Set<Card> sevenCards = cardsFromString("2H 2S 2D 13D 12S 11C 10C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.THREE_OF_A_KIND);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 2S 2D 13D 12S"));
   }

   @Test
   public void threeNormalKindWithAceInRemaining() {
      Set<Card> sevenCards = cardsFromString("2H 2S 2D 13D 12S 11C 1C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.THREE_OF_A_KIND);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 2S 2D 13D 1C"));
   }

   @Test
   public void twoPairsWithNormalRemainingHighCard() {
      Set<Card> sevenCards = cardsFromString("2H 2S 13S 13D 12S 11C 7C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.TWO_PAIR);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 2S 13S 13D 12S"));
   }

   @Test
   public void twoPairsWithAceInRemaining() {
      Set<Card> sevenCards = cardsFromString("2H 2S 13S 13D 12S 11C 1C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.TWO_PAIR);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 2S 13S 13D 1C"));
   }

   @Test
   public void threeNormalPairsWithNormalRemainingHighCard() {
      Set<Card> sevenCards = cardsFromString("2H 2S 13S 13D 10S 10C 9C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.TWO_PAIR);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("13S 13D 10S 10C 9C"));
   }

   @Test
   public void threeNormalPairsWithAceInRemaining() {
      Set<Card> sevenCards = cardsFromString("2H 2S 13S 13D 10S 10C 1C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.TWO_PAIR);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("13S 13D 10S 10C 1C"));
   }

   @Test
   public void twoNormalPairsAndOnePairOfAce() {
      Set<Card> sevenCards = cardsFromString("2H 2S 13S 13D 1S 1C 9C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.TWO_PAIR);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("13S 13D 1S 1C 9C"));
   }


   @Test
   public void onePairsWithoutAceInRemaining() {
      Set<Card> sevenCards = cardsFromString("2H 2S 13S 12D 7S 6C 9C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.ONE_PAIR);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 2S 13S 12D 9C"));
   }

   @Test
   public void onePairsWithAceInRemaining() {
      Set<Card> sevenCards = cardsFromString("2H 2S 13S 12D 7S 1C 9C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.ONE_PAIR);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("2H 2S 13S 12D 1C"));
   }

   @Test
   public void highCardWithoutAce() {
      Set<Card> sevenCards = cardsFromString("2H 5S 13S 12D 7S 10C 9C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.HIGH_CARD);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("13S 12D 7S 10C 9C"));
   }

   @Test
   public void highCardWitAce() {
      Set<Card> sevenCards = cardsFromString("1H 5S 13S 12D 7S 10C 9C");

      PokerHand pokerHand = pokerHandService.getBestPokerHand(sevenCards);

      assertThat(pokerHand.getPokerHandType()).isEqualTo(PokerHandType.HIGH_CARD);
      assertThat(pokerHand.getCards()).containsAll(cardsFromString("13S 12D 1H 10C 9C"));
   }

}