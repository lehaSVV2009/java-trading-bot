package alexsoroka.bots;

/**
 * Bidder with always 0 bids.
 */
public class ZeroOnlyBidder implements Bidder {

  /**
   * Do nothing.
   */
  @Override
  public void init(int quantity, int cash) {
  }

  /**
   * @return always 0
   */
  @Override
  public int placeBid() {
    return 0;
  }

  /**
   * Do nothing.
   */
  @Override
  public void bids(int own, int other) {
  }
}
