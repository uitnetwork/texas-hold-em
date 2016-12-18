package com.uitnetwork.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ninhdoan on 12/15/16.
 */
public class CardTest {

   @Test(expected = IllegalArgumentException.class)
   public void canNotCreateCardWithoutCardRank() {
      new Card(null, CardType.CLUB);
   }

   @Test(expected = IllegalArgumentException.class)
   public void canNotCreateCardWithoutCardType() {
      new Card(CardRank.ACE, null);
   }

   @Test
   public void canCreateCardWithCorrectInformation() {
      CardRank cardRank = CardRank.ACE;
      CardType cardType = CardType.CLUB;
      Card card = new Card(cardRank, cardType);

      assertThat(card.getRank()).isEqualTo(cardRank);
      assertThat(card.getType()).isEqualTo(cardType);
   }

}