package alexsoroka.bots;

import alexsoroka.util.Assert;

import java.util.Random;

/**
 * Bidder with default validations and initializations
 */
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

    afterInit(quantity, cash);
  }

  /**
   * Additional init
   *
   * @param quantity the quantity
   * @param cash     the cash limit
   */
  protected void afterInit(int quantity, int cash) {

  }

  /**
   * @throws IllegalArgumentException if own or other are negative numbers
   */
  @Override
  public void bids(int own, int other) {
    Assert.isTrue(own >= 0, "Own bid must be a positive number");
    Assert.isTrue(other >= 0, "Other bid must be a positive number");

    ownCash -= own;
    opponentCash -= other;

    afterBids(own, other);
  }

  /**
   * Additional logic to show the bids of the two bidders.
   *
   * @param own   the bid of this bidder
   * @param other the bid of the other bidder
   */
  protected void afterBids(int own, int other) {

  }

  /**
   * @return if bid is less than or equal than bid, otherwise random value with cash limit
   */
  protected int randomIfGreaterThanCash(int bid, int cash) {
    return bid <= cash ? bid : new Random().nextInt(cash);
  }

  /**
   * @return if bid is less than or equal than bid, otherwise 0
   */
  protected int zeroIfGreaterThanCash(int bid, int cash) {
    return bid <= cash ? bid : 0;
  }

}
