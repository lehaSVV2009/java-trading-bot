package alexsoroka.bots;

/**
 * Represents a bidder for the action.
 */
public interface Bidder {

  /**
   * Initializes the bidder with the production quantity and the allowed cash limit.
   *
   * @param quantity the quantity
   * @param cash     the cash limit
   */
  void init(int quantity, int cash);

  /**
   * Retrieves the next bid for the product, which may be zero.
   *
   * @return the next bid
   */
  int placeBid();

  /**
   * Shows the bids of the two bidders.
   *
   * @param own   the bid of this bidder
   * @param other the bid of the other bidder
   */
  void bids(int own, int other);

}
