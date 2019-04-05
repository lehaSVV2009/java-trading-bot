package alexsoroka.bots;

import alexsoroka.util.Assert;

public abstract class AbstractBidder implements Bidder {

  /**
   * Initial number of products to sell.
   */
  protected int initialQuantity;

  /**
   * Current value of bidder money. 0 by default.
   */
  protected int ownCash;

  /**
   * Current value of opponent money. 0 by default.
   */
  protected int opponentCash;

  /**
   * @throws IllegalArgumentException if quantity or ownCash are negative numbers or if quantity is not even
   */
  @Override
  public void init(int quantity, int cash) {
    Assert.isTrue(quantity >= 0, "Quantity must be a positive number");
    Assert.isTrue(quantity % 2 == 0, "Quantity must be even");
    Assert.isTrue(cash >= 0, "Cash must be a positive number");

    this.initialQuantity = quantity;
    this.ownCash = cash;
    this.opponentCash = cash;

    initialize(quantity, cash);
  }

  public abstract void initialize(int quantity, int cash);

  /**
   * @throws IllegalArgumentException if own or other are negative numbers
   */
  @Override
  public void bids(int own, int other) {
    Assert.isTrue(own >= 0, "Own bid must be a positive number");
    Assert.isTrue(other >= 0, "Other bid must be a positive number");

    ownCash -= own;
    opponentCash -= other;

    storeBids(own, other);
  }

  public abstract void storeBids(int own, int other);
}
