package bots;

import auction.Bidder;

/**
 * Bidder with always 0 bids.
 */
public class AlwaysZeroBidder implements Bidder {

  /**
   * Initializes the bidder with the production quantity and the allowed cash limit.
   *
   * @param quantity the quantity
   * @param cash     the cash limit
   */
  @Override
  public void init(int quantity, int cash) {
  }

  /**
   * Retrieves the next bid for the product, which is always zero.
   *
   * @return always 0
   */
  @Override
  public int placeBid() {
    return 0;
  }

  /**
   * Shows the bids of the two bidders.
   *
   * @param own   the bid of this bidder
   * @param other the bid of the other bidder
   */
  @Override
  public void bids(int own, int other) {
  }
}
