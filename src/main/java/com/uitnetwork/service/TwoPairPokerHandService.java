package com.uitnetwork.service;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;

import java.util.Set;

/**
 * Created by ninhdoan on 12/17/16.
 */
public interface TwoPairPokerHandService {

   PokerHand getBestTwoPairPokerHand(Set<Card> sevenCards);
}
