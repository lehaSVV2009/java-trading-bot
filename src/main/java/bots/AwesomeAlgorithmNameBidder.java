package bots;

import auction.Bidder;

import java.util.Random;

/**
 * Bidder with random bids.
 */
public class AwesomeAlgorithmNameBidder implements Bidder {
  /**
   * Amount of products at the start of auction
   */
  private int quantity;

  /**
   * Current value of bidder money
   */
  private int cash;

  /**
   * Initializes the bidder with the production quantity and the allowed cash limit.
   *
   * @param quantity the quantity
   * @param cash     the cash limit
   * @throws IllegalArgumentException if quantity or cash are negative numbers
   */
  @Override
  public void init(int quantity, int cash) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity must be a positive number");
    }
    if (cash < 0) {
      throw new IllegalArgumentException("Cash must be a positive number");
    }
    this.cash = cash;
  }

  /**
   * Randomly retrieves the next bid for the product, which may be zero.
   *
   * @return the next random bid
   */
  @Override
  public int placeBid() {
    return new Random().nextInt(cash + 1);
  }

  /**
   * Shows the bids of the two bidders.
   *
   * @param own   the bid of this bidder
   * @param other the bid of the other bidder
   * @throws IllegalArgumentException if own or other are negative numbers
   */
  @Override
  public void bids(int own, int other) {
    if (own < 0) {
      throw new IllegalArgumentException("Own bid must be a positive number");
    }
    if (other < 0) {
      throw new IllegalArgumentException("Other bid must be a positive number");
    }
    // TODO ask if it should be thread-safe
    this.cash -= own;
  }
}
