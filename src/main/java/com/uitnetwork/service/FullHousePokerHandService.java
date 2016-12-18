package com.uitnetwork.service;

import com.uitnetwork.domain.Card;
import com.uitnetwork.domain.PokerHand;

import java.util.Set;

/**
 * Created by ninhdoan on 12/15/16.
 */
public interface FullHousePokerHandService {

   PokerHand getBestFullHousePokerHand(Set<Card> sevenCards);
}
