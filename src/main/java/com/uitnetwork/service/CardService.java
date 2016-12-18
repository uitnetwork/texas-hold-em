package com.uitnetwork.service;

import com.uitnetwork.domain.Card;

import java.util.List;
import java.util.Set;

/**
 * Created by ninhdoan on 12/15/16.
 */
public interface CardService {
   Set<Card> getAtMostSameTypeCards(Set<Card> cards);

   List<Card> getAtMostStraightCardsOrderByRank(Set<Card> cards);

   Set<Card> getAtMostSameRankCards(Set<Card> cards);

   Set<Card> getHighestThreeOfAKind(Set<Card> cards);

   Set<Card> getHighestPair(Set<Card> cards);

   Card getHighestCard(Set<Card> cards);

   Set<Card> getHighestCards(Set<Card> cards, int numberOfHighestCards);

   Set<Card> getAllCardsInSourceExcludeCard(Set<Card> sourceCards, Card excludingCard);

   Set<Card> getAllCardsInSourceExcludeCards(Set<Card> sourceCards, Set<Card> excludingCards);
}
