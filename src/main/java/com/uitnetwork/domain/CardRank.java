package com.uitnetwork.domain;

import lombok.Getter;

/**
 * Created by ninhdoan on 12/15/16.
 */
@Getter
public enum CardRank {

   ACE,
   TWO,
   THREE,
   FOUR,
   FIVE,
   SIX,
   SEVEN,
   EIGHT,
   NINE,
   TEN,
   JACK,
   QUEEN,
   KING;

   private static CardRank[] ALL_VALUES = values();

   public CardRank next() {
      return ALL_VALUES[(this.ordinal() + 1) % ALL_VALUES.length];
   }
}
