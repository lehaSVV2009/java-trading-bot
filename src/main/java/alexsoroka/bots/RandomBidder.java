package alexsoroka.bots;

import alexsoroka.util.Assert;

import java.util.Random;

/**
 * Bidder with random bids.
 */
public class RandomBidder implements Bidder {

  /**
   * Current value of bidder money
   */
  private int cash;

  /**
   * @throws IllegalArgumentException if quantity or cash are negative numbers
   */
  @Override
  public void init(int quantity, int cash) {
    Assert.isTrue(quantity >= 0, "Quantity must be a positive number");
    Assert.isTrue(cash >= 0, "Cash must be a positive number");

    this.cash = cash;
  }

  /**
   * @return the next random bid
   */
  @Override
  public int placeBid() {
    return new Random().nextInt(cash + 1);
  }

  /**
   * @throws IllegalArgumentException if own or other are negative numbers
   */
  @Override
  public void bids(int own, int other) {
    Assert.isTrue(own >= 0, "Own bid must be a positive number");
    Assert.isTrue(other >= 0, "Other bid must be a positive number");

    this.cash -= own;
  }
}
