package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.CardRank;
import com.uitnetwork.domain.CardType;
import com.uitnetwork.service.CardService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by ninhdoan on 12/15/16.
 */
@Service
public class CardServiceImpl implements CardService {

   private static Comparator<List<Card>> highestRankList() {
      return (firstCards, secondCards) -> {
         Card firstCardInFirst = firstCards.get(0);
         Card firstCardInSecond = secondCards.get(0);

         if (firstCardInFirst.getRank() == CardRank.ACE) return 1;
         if (firstCardInSecond.getRank() == CardRank.ACE) return -1;

         return firstCardInFirst.getRank().compareTo(firstCardInSecond.getRank());
      };
   }

   @Override
   public Set<Card> getAtMostSameTypeCards(Set<Card> cards) {
      Set<Card> atMostSameTypeCards = new HashSet<>();
      Map<CardType, List<Card>> cardTypeSetMap = cards.stream().collect(Collectors.groupingBy(Card::getType));
      cardTypeSetMap.values().stream().max(Comparator.comparingInt(List::size)).ifPresent(atMostSameTypeCards::addAll);
      return atMostSameTypeCards;
   }

   @Override
   public List<Card> getAtMostStraightCardsOrderByRank(Set<Card> cards) {
      List<Card> orderByRankCards = cards.stream()
            .sorted(Comparator.comparing(Card::getRank))
            .collect(Collectors.toList());

      boolean isSpecial = false;
      int currentStart = 0;
      int currentSize = 1;
      int atMostStraightStart = 0;
      int atMostStraightSize = 1;

      for (int i = 1; i < orderByRankCards.size(); ++i) {
         Card previous = orderByRankCards.get(i - 1);
         Card current = orderByRankCards.get(i);
         if (previous.getRank() == current.getRank()) {
            continue;
         }

         if (previous.getRank().next() == current.getRank()) {
            currentSize++;
            continue;
         }

         if (currentSize > atMostStraightSize) {
            // keep track of atMostStraight
            atMostStraightStart = currentStart;
            atMostStraightSize = currentSize;
         }

         // reset
         currentStart = i;
         currentSize = 1;

      }
      Card lastCard = orderByRankCards.get(orderByRankCards.size() - 1);
      Card firstCard = orderByRankCards.get(0);
      if (lastCard.getRank() == CardRank.KING && firstCard.getRank() == CardRank.ACE) {
         // add one more to a special case
         currentSize++;
         isSpecial = true;
      }

      if (currentSize > atMostStraightSize) {
         // compare with the last atMostStraightSize to get the final result
         atMostStraightStart = currentStart;
         atMostStraightSize = currentSize;
      }

      if (isSpecial) {
         List<Card> atMostStraightCards = getConsecutiveCardsFromStartToEnd(orderByRankCards, atMostStraightStart, atMostStraightSize - 1);
         atMostStraightCards.add(firstCard);

         return atMostStraightCards;
      } else {
         List<Card> atMostStraightCards = getConsecutiveCardsFromStartToEnd(orderByRankCards, atMostStraightStart, atMostStraightSize);
         return atMostStraightCards;
      }

   }

   private List<Card> getConsecutiveCardsFromStartToEnd(List<Card> orderByRankCards, int start, int numberOfConsecutiveCards) {
      List<Card> consecutiveCards = new ArrayList<>();

      Card cardAtIndex = orderByRankCards.get(start);
      consecutiveCards.add(cardAtIndex);

      Card previous = cardAtIndex;


      for (int i = start + 1; i < orderByRankCards.size() && consecutiveCards.size() != numberOfConsecutiveCards; ++i) {
         Card current = orderByRankCards.get(i);
         if (previous.getRank() == current.getRank()) {
            continue;
         }
         consecutiveCards.add(current);
         previous = current;
      }

      return consecutiveCards;
   }

   @Override
   public Set<Card> getAtMostSameRankCards(Set<Card> cards) {
      Set<Card> atMostSameRankCards = new HashSet<>();
      Map<CardRank, List<Card>> cardMapByRank = cards.stream().collect(Collectors.groupingBy(Card::getRank));
      cardMapByRank.values().stream().max(Comparator.comparingInt(List::size)).ifPresent(atMostSameRankCards::addAll);
      return atMostSameRankCards;
   }

   @Override
   public Set<Card> getHighestThreeOfAKind(Set<Card> cards) {
      return getHighestSameRankCards(cards, 3);
   }

   private Set<Card> getHighestSameRankCards(Set<Card> cards, int numberOfCardsHaveSameRank) {
      Set<Card> highestSameRankCards = new HashSet<>();
      Map<CardRank, List<Card>> cardMapByRank = cards.stream().collect(Collectors.groupingBy(Card::getRank));

      cardMapByRank.values().stream()
            .filter(sameRankCards -> sameRankCards.size() >= numberOfCardsHaveSameRank)
            .max(highestRankList())
            .ifPresent(highestSameRankCards::addAll);

      return highestSameRankCards.stream()
            .limit(numberOfCardsHaveSameRank)
            .collect(Collectors.toSet());
   }

   @Override
   public Set<Card> getHighestPair(Set<Card> cards) {
      return getHighestSameRankCards(cards, 2);
   }

   @Override
   public Card getHighestCard(Set<Card> cards) {
      List<Card> orderByRankCards = cards.stream()
            .sorted(Comparator.comparing(Card::getRank))
            .collect(Collectors.toList());

      Card firstCard = orderByRankCards.get(0);
      Card lastCard = orderByRankCards.get(orderByRankCards.size() - 1);
      if (firstCard.getRank() == CardRank.ACE) {
         return firstCard;
      }
      return lastCard;
   }

   @Override
   public Set<Card> getHighestCards(Set<Card> cards, int numberOfHighestCards) {
      Set<Card> highestCards = new HashSet<>();

      List<Card> orderByRankCards = cards.stream()
            .sorted(Comparator.comparing(Card::getRank))
            .collect(Collectors.toList());
      int numberOfCardAdded = 0;
      // process ACE first
      for (Card card : orderByRankCards) {
         if (card.getRank() != CardRank.ACE) {
            break;
         }
         numberOfCardAdded++;
         highestCards.add(card);
      }

      int remainingNeededCards = numberOfHighestCards - numberOfCardAdded;
      int remainingNeededCardsIndex = cards.size() - remainingNeededCards;
      for (int i = remainingNeededCardsIndex; i < orderByRankCards.size(); ++i) {
         Card card = orderByRankCards.get(i);
         highestCards.add(card);
      }

      return highestCards;
   }

   @Override
   public Set<Card> getAllCardsInSourceExcludeCard(Set<Card> sourceCards, Card excludingCard) {
      return getAllCardsInSourceExcludeCards(sourceCards, Collections.singleton(excludingCard));
   }

   @Override
   public Set<Card> getAllCardsInSourceExcludeCards(Set<Card> sourceCards, Set<Card> excludingCards) {
      Predicate<Card> cardInExcludingCards = excludingCards::contains;
      return sourceCards.stream().filter(cardInExcludingCards.negate()).collect(Collectors.toSet());
   }

}
