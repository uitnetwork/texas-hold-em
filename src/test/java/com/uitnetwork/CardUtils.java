package com.uitnetwork;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.CardRank;
import com.uitnetwork.domain.CardType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ninhdoan on 12/15/16.
 */
public class CardUtils {

   public static final String STRING_WITH_6_CARDS = "1H 2H 3H 4H 5H 6H";

   public static final String STRING_WITH_8_CARDS = "1H 2H 3H 4H 5H 6H 7H 8H";

   public static final Set<Card> cardsFromString(String cardsString) {
      String[] cardsStringArray = cardsString.split(" ");
      return Arrays.asList(cardsStringArray).stream().map(CardUtils::aCardFromString).collect(Collectors.toSet());
   }

   public static final List<Card> cardsFromStringByOrder(String cardsString) {
      List<Card> cardsByOrder = new ArrayList<>();
      String[] cardsStringArray = cardsString.split(" ");
      for (int i = 0; i < cardsStringArray.length; ++i) {
         cardsByOrder.add(aCardFromString(cardsStringArray[i]));
      }

      return cardsByOrder;
   }

   public static final Card aCardFromString(String cardString) {
      String uppercaseAndTrimCardString = cardString.toUpperCase().trim();
      int lastCharIndex = cardString.length() - 1;
      char cardTypeChar = uppercaseAndTrimCardString.charAt(lastCharIndex);
      int cardRankNum = Integer.parseInt(uppercaseAndTrimCardString.substring(0, lastCharIndex));

      return new Card(getCardRankFromNumber(cardRankNum), getCardTypeFromCharacter(cardTypeChar));
   }

   private static final CardType getCardTypeFromCharacter(char cardTypeChar) {
      switch (cardTypeChar) {
         case 'S':
            return CardType.SPADE;
         case 'C':
            return CardType.CLUB;
         case 'D':
            return CardType.DIAMOND;
         case 'H':
            return CardType.HEART;
         default:
            throw new IllegalArgumentException(cardTypeChar + " is not valid");
      }
   }

   private static final CardRank getCardRankFromNumber(int cardRankNum) {
      assertThat(cardRankNum).isLessThanOrEqualTo(CardRank.values().length);

      return CardRank.values()[cardRankNum - 1];
   }
}
