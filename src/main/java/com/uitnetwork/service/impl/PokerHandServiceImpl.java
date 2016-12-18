package com.uitnetwork.service.impl;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;
import com.uitnetwork.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by ninhdoan on 12/17/16.
 */
@Service
public class PokerHandServiceImpl implements PokerHandService {

   @Autowired
   private StraightFlushPokerHandService straightFlushPokerHandService;

   @Autowired
   private FourOfAKindPokerHandService fourOfAKindPokerHandService;

   @Autowired
   private FullHousePokerHandService fullHousePokerHandService;

   @Autowired
   private FlushPokerHandService flushPokerHandService;

   @Autowired
   private StraightPokerHandService straightPokerHandService;

   @Autowired
   private ThreeOfAKindPokerHandService threeOfAKindPokerHandService;

   @Autowired
   private TwoPairPokerHandService twoPairPokerHandService;

   @Autowired
   private OnePairPokerHandService onePairPokerHandService;

   @Autowired
   private HighCardPokerHandService highCardPokerHandService;

   @Override
   public PokerHand getBestPokerHand(Set<Card> sevenCards) {
      PokerHand pokerHand = getBestPokerHandOrderByPriority(sevenCards,
            straightFlushPokerHandService::getBestStraightFlushPokerHand,
            fourOfAKindPokerHandService::getBestFourOfAKindPokerHand,
            fullHousePokerHandService::getBestFullHousePokerHand,
            flushPokerHandService::getBestFlushPokerHand,
            straightPokerHandService::getBestStraightPokerHand,
            threeOfAKindPokerHandService::getBestThreeOfAKindPokerHand,
            twoPairPokerHandService::getBestTwoPairPokerHand,
            onePairPokerHandService::getBestOnePairPokerHand,
            highCardPokerHandService::getBestHighCardPokerHand);
      return pokerHand;
   }

   private PokerHand getBestPokerHandOrderByPriority(Set<Card> sevenCards, PokerHandFunction... pokerHandFunctionsOrderByPriority) {
      PokerHand pokerHand;
      for (PokerHandFunction pokerHandFunction : pokerHandFunctionsOrderByPriority) {
         pokerHand = pokerHandFunction.getPokerHand(sevenCards);
         if (pokerHand != null) {
            return pokerHand;
         }
      }

      throw new RuntimeException("Should at least have high card poker hand!");
   }


   @FunctionalInterface
   private static interface PokerHandFunction {
      PokerHand getPokerHand(Set<Card> sevenCards);
   }

}
