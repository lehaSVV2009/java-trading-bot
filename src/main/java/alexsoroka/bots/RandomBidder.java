package alexsoroka.bots;

import java.util.Random;

/**
 * Bidder with random bids.
 */
public class RandomBidder extends AbstractBidder {
  /**
   * @return the next random bid
   */
  @Override
  public int placeBid() {
    return new Random().nextInt(ownCash + 1);
  }
}
