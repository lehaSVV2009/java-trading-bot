package alexsoroka.bots;

import alexsoroka.common.WinFunctions;

import java.util.Random;

/**
 * Bidder with bids that depend on previous winner's bid.
 */
public class WinnerPlusOneOrTwoBidder extends AbstractBidder {

  /**
   * Last own's bid. 0 by default.
   */
  private int lastOwnBid;

  /**
   * Last other's bid. 0 by default.
   */
  private int lastOpponentBid;

  @Override
  protected void afterInit(int quantity, int cash) {
    this.lastOwnBid = 0;
    this.lastOpponentBid = 0;
  }

  /**
   * @return the next bid that it is the same as previous winner's bid plus one
   */
  @Override
  public int placeBid() {
    int previousWinnerBid = WinFunctions.findWinnerBid(lastOpponentBid, lastOwnBid);
    int nextValue = previousWinnerBid + (new Random().nextBoolean() ? 1 : 2);
    return zeroIfGreaterThanCash(nextValue, ownCash);
  }

  @Override
  protected void afterBids(int own, int other) {
    this.lastOwnBid = own;
    this.lastOpponentBid = other;
  }
}
