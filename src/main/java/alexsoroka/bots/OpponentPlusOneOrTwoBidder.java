package alexsoroka.bots;

import java.util.Random;

/**
 * Bidder with bids that depend on previous opponent's bid.
 */
public class OpponentPlusOneOrTwoBidder extends AbstractBidder {

  /**
   * Last other's bid. 0 by default.
   */
  private int lastOpponentBid;

  @Override
  protected void afterInit(int quantity, int cash) {
    this.lastOpponentBid = 0;
  }

  /**
   * @return bid that it is the same as previous opponent's bid plus one or two
   */
  @Override
  public int placeBid() {
    int nextValue = lastOpponentBid + (new Random().nextBoolean() ? 1 : 2);
    return zeroIfGreaterThanCash(nextValue, ownCash);
  }

  @Override
  protected void afterBids(int own, int other) {
    this.lastOpponentBid = other;
  }
}
