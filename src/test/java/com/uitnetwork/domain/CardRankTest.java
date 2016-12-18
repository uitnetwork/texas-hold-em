package com.uitnetwork.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by ninhdoan on 12/15/16.
 */
public class CardRankTest {

   @Test
   public void nextOfCardAceShouldBeCardTwo() {
      Assertions.assertThat(CardRank.ACE.next()).isEqualTo(CardRank.TWO);
   }

   @Test
   public void nextOfCardKingShouldBeAce() {
      Assertions.assertThat(CardRank.KING.next()).isEqualTo(CardRank.ACE);
   }

   @Test
   public void nextInTheMiddleShouldBeTheNextOne() {
      Assertions.assertThat(CardRank.TEN.next()).isEqualTo(CardRank.JACK);
   }

}