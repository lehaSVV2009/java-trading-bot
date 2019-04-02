package alexsoroka.bots;

import alexsoroka.util.Assert;

/**
 * Bidder with bids that bids average from the history value plus one.
 */
public class AveragePlusOneBidder implements Bidder {

  /**
   * Sum of all the bids (own and opposite)
   */
  private int allBidsSum;

  /**
   * Number of all the bids
   */
  private int allBidsCount;

  /**
   * Current value of bidder money. 0 by default.
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
    this.allBidsSum = 0;
    this.allBidsCount = 0;
  }

  /**
   * @return the average bid from all the bids in the auction
   */
  @Override
  public int placeBid() {
    double average = ((double) allBidsSum) / allBidsCount;
    int nextValue = (int) (Math.round(average)) + 1;
    return nextValue <= cash ? nextValue : 0;
  }

  /**
   * @throws IllegalArgumentException if own or other are negative numbers
   */
  @Override
  public void bids(int own, int other) {
    Assert.isTrue(own >= 0, "Own bid must be a positive number");
    Assert.isTrue(other >= 0, "Other bid must be a positive number");

    this.cash -= own;
    this.allBidsSum += (own + other);
    this.allBidsCount += 2;
  }
}
