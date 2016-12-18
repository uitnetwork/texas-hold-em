package com.uitnetwork.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

/**
 * Created by ninhdoan on 12/15/16.
 */
@Getter
@EqualsAndHashCode(of = {"rank", "type"})
@ToString
public class Card {

   private CardRank rank;

   private CardType type;

   public Card(CardRank rank, CardType type) {
      Assert.notNull(rank, "rank should not be null");
      Assert.notNull(type, "type should not be null");
      this.rank = rank;
      this.type = type;
   }
}
