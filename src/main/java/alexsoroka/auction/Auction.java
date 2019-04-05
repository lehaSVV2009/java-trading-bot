package alexsoroka.auction;

import alexsoroka.bots.Bidder;

import java.util.Map;

/**
 * Auction actions
 */
public interface Auction {

  /**
   * Play bets with all products
   *
   * @return bidder <-> bidder auction result
   */
  Map<Bidder, BidderAuctionResult> run();
}