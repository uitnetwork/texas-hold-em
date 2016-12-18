package com.uitnetwork.service.impl;

import com.uitnetwork.AbstractMockitoTests;
import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.CardRank;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Set;

import static com.uitnetwork.CardUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ninhdoan on 12/15/16.
 */
public class CardServiceImplTest extends AbstractMockitoTests {

   @InjectMocks
   private CardServiceImpl cardService;

   @Test
   public void atMostSameTypeCardsWins() {
      Set<Card> cards = cardsFromString("1H 2H 5H 6H 7H 9S 10D");
      Set<Card> atMostSameTypeCards = cardService.getAtMostSameTypeCards(cards);

      assertThat(atMostSameTypeCards).containsAll(cardsFromString("1H 2H 5H 6H 7H"));
   }

   @Test
   public void twoEqualLengthSameTypeCardsOnlySureAboutLength() {
      Set<Card> cards = cardsFromString("1H 2H 5H 6S 7S 9S 10D");
      Set<Card> atMostSameTypeCards = cardService.getAtMostSameTypeCards(cards);

      assertThat(atMostSameTypeCards).hasSize(3);
   }

   @Test
   public void atMostStraightCardsWinsMustReturnInOrder() {
      Set<Card> cards = cardsFromString("1H 2H 5H 6S 7S 9S 10D");
      List<Card> atMostStraightCards = cardService.getAtMostStraightCardsOrderByRank(cards);

      assertThat(atMostStraightCards).containsExactlyElementsOf(cardsFromStringByOrder("5H 6S 7S"));
   }

   @Test
   public void atMostStraightCardsNotReturnDuplicatedSameRankCard() {
      Set<Card> cards = cardsFromString("1H 2H 1S 2S 3S 8S 9D");
      List<Card> atMostStraightCards = cardService.getAtMostStraightCardsOrderByRank(cards);

      assertThat(atMostStraightCards).hasSize(3);
      assertThat(atMostStraightCards.get(0).getRank()).isEqualTo(CardRank.ACE);
      assertThat(atMostStraightCards.get(1).getRank()).isEqualTo(CardRank.TWO);
      assertThat(atMostStraightCards.get(2).getRank()).isEqualTo(CardRank.THREE);
   }

   @Test
   public void atMostStraightCardsWinsInSpecialMustReturnInOrder() {
      Set<Card> cards = cardsFromString("1H 2H 3H 8H 11S 12S 13D");
      List<Card> atMostStraightCards = cardService.getAtMostStraightCardsOrderByRank(cards);

      assertThat(atMostStraightCards).containsExactlyElementsOf(cardsFromStringByOrder("11S 12S 13D 1H"));
   }

   @Test
   public void returnAtMostSameRankCardsWhenHaveOnlyOne() {
      Set<Card> cards = cardsFromString("1H 2H 3H 12H 11S 12S 13D");

      Set<Card> atMostSameRankCards = cardService.getAtMostSameRankCards(cards);

      assertThat(atMostSameRankCards).containsAll(cardsFromString("12H 12S"));
   }

   @Test
   public void twoEqualLengthSameRankCardsOnlySureAboutLength() {
      Set<Card> cards = cardsFromString("1H 2H 3H 12H 11S 12S 11D");

      Set<Card> atMostSameRankCards = cardService.getAtMostSameRankCards(cards);

      assertThat(atMostSameRankCards).hasSize(2);
   }

   @Test
   public void onlyOneThreeOfAKind() {
      Set<Card> cards = cardsFromString("1H 2H 3H 12H 12D 12S 13D");

      Set<Card> atMostSameRankCards = cardService.getHighestThreeOfAKind(cards);

      assertThat(atMostSameRankCards).containsAll(cardsFromString("12H 12S 12D"));
   }

   @Test
   public void returnHighestThreeOfAKindWhenThereAreFourOfAKind() {
      Set<Card> cards = cardsFromString("1H 2H 12C 12H 12D 12S 13D");

      Set<Card> atMostSameRankCards = cardService.getHighestThreeOfAKind(cards);

      assertThat(atMostSameRankCards).hasSize(3);
      assertThat(atMostSameRankCards).allMatch(card -> card.getRank() == CardRank.QUEEN);
   }


   @Test
   public void returnThreeOfAKindWhenMultipleThreeOfAKindNotHaveThreeAces() {
      Set<Card> cards = cardsFromString("2D 2H 2S 12H 12S 12D 13D");

      Set<Card> atMostSameRankCards = cardService.getHighestThreeOfAKind(cards);

      assertThat(atMostSameRankCards).containsAll(cardsFromString("12H 12S 12D"));
   }

   @Test
   public void returnThreeAcesWhenMultipleThreeOfAKindHaveAces() {
      Set<Card> cards = cardsFromString("1D 1H 1S 12H 12S 12D 13D");

      Set<Card> atMostSameRankCards = cardService.getHighestThreeOfAKind(cards);

      assertThat(atMostSameRankCards).containsAll(cardsFromString("1D 1H 1S"));
   }


   @Test
   public void onlyOnePair() {
      Set<Card> cards = cardsFromString("1H 2H 3H 12H 12D 11S 13D");

      Set<Card> atMostSameRankCards = cardService.getHighestPair(cards);

      assertThat(atMostSameRankCards).containsAll(cardsFromString("12H 12D"));
   }

   @Test
   public void returnPairWhenThereAreMoreThan2SameCards() {
      Set<Card> cards = cardsFromString("1H 2H 12C 12H 12D 12S 13D");

      Set<Card> atMostSameRankCards = cardService.getHighestPair(cards);

      assertThat(atMostSameRankCards).hasSize(2);
      assertThat(atMostSameRankCards).allMatch(card -> card.getRank() == CardRank.QUEEN);
   }


   @Test
   public void returnHighestPairWhenMultiplePairsNotHaveThreeAces() {
      Set<Card> cards = cardsFromString("2D 2H 2S 12H 12S 11D 13D");

      Set<Card> atMostSameRankCards = cardService.getHighestPair(cards);

      assertThat(atMostSameRankCards).containsAll(cardsFromString("12H 12S"));
   }

   @Test
   public void returnHighestPairWhenMultiplePairsHaveAces() {
      Set<Card> cards = cardsFromString("1D 1H 13S 12H 12S 12D 13D");

      Set<Card> atMostSameRankCards = cardService.getHighestPair(cards);

      assertThat(atMostSameRankCards).containsAll(cardsFromString("1D 1H"));
   }


   @Test
   public void returnNormalCardAsHighestRank() {
      Set<Card> cards = cardsFromString("2H 3H 12H 11S 12S 13D");

      Card card = cardService.getHighestCard(cards);

      assertThat(card).isEqualTo(aCardFromString("13D"));
   }

   @Test
   public void alwaysReturnAceAsHighestRankIfPresent() {
      Set<Card> cards = cardsFromString("1H 2H 3H 12H 11S 12S 11D");

      Card card = cardService.getHighestCard(cards);

      assertThat(card).isEqualTo(aCardFromString("1H"));
   }

   @Test
   public void onlySureAboutRankWhenHaveMultipleSameHighestNormalRankCards() {
      Set<Card> cards = cardsFromString("2H 3H 12H 11S 13S 13D");

      Card card = cardService.getHighestCard(cards);

      assertThat(card.getRank()).isEqualTo(CardRank.KING);
   }

   @Test
   public void onlySureAboutRankWhenHaveMultipleAces() {
      Set<Card> cards = cardsFromString("1H 2H 3H 12H 1S 12S 11D");

      Card card = cardService.getHighestCard(cards);

      assertThat(card.getRank()).isEqualTo(CardRank.ACE);
   }

   @Test
   public void returnHighestCardsWithoutAce() {
      Set<Card> cards = cardsFromString("2H 3H 11H 11S 12S 13D");

      Set<Card> twoHighestCards = cardService.getHighestCards(cards, 2);

      assertThat(twoHighestCards).hasSize(2);
      assertThat(twoHighestCards).containsAll(cardsFromString("13D 12S"));
   }

   @Test
   public void returnHighestCardsWithAce() {
      Set<Card> cards = cardsFromString("1H 3H 11H 11S 12S 13D");

      Set<Card> twoHighestCards = cardService.getHighestCards(cards, 2);

      assertThat(twoHighestCards).hasSize(2);
      assertThat(twoHighestCards).containsAll(cardsFromString("13D 1H"));
   }

   @Test
   public void getAllCardsInSourceExcludeCard() {
      Set<Card> sourceCards = cardsFromString("1H 2H 3H 12H 1S 12S 11D");
      Card excludingCard = aCardFromString("1H");

      Set<Card> remainingCards = cardService.getAllCardsInSourceExcludeCard(sourceCards, excludingCard);

      assertThat(remainingCards).hasSize(sourceCards.size() - 1);
      assertThat(remainingCards).containsAll(cardsFromString("2H 3H 12H 1S 12S 11D"));

   }

   @Test
   public void getAllCardsInSourceExcludeCards() {
      Set<Card> sourceCards = cardsFromString("1H 2H 3H 12H 1S 12S 11D");
      Set<Card> excludingCards = cardsFromString("1H 2H");

      Set<Card> remainingCards = cardService.getAllCardsInSourceExcludeCards(sourceCards, excludingCards);

      assertThat(remainingCards).hasSize(sourceCards.size() - excludingCards.size());
      assertThat(remainingCards).containsAll(cardsFromString("3H 12H 1S 12S 11D"));

   }

}