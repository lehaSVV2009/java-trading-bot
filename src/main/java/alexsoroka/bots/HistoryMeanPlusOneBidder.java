package alexsoroka.bots;

/**
 * Bidder with bids that bids average from the history value plus one.
 */
public class HistoryMeanPlusOneBidder extends AbstractBidder {

  /**
   * Sum of all the bids (own and opposite)
   */
  private int allBidsSum;

  /**
   * Number of all the bids
   */
  private int allBidsCount;

  @Override
  public void afterInit(int quantity, int cash) {
    this.allBidsSum = 0;
    this.allBidsCount = 0;
  }

  /**
   * @return the average bid from all the bids in the auction
   */
  @Override
  public int placeBid() {
    // Skip calculations if there is no cash
    if (ownCash == 0) {
      return 0;
    }

    double average = ((double) allBidsSum) / allBidsCount;
    int nextValue = (int) (Math.round(average)) + 1;
    return zeroIfGreaterThanCash(nextValue, ownCash);
  }

  @Override
  protected void afterBids(int own, int other) {
    this.allBidsSum += (own + other);
    this.allBidsCount += 2;
  }
}
