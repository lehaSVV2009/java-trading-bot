package alexsoroka.bots;

import alexsoroka.util.Assert;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.Queue;


/**
 * Bidder with bids that bids median value from the recent winner history plus one.
 */
public class WinnerMedianPlusOneBidder implements Bidder {

  /**
   * Recent 100 winner bids
   */
  private final Queue<Integer> history = new CircularFifoQueue<>(100);

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
    this.history.clear();
  }

  /**
   * @return the average bid from all the bids in the auction
   */
  @Override
  public int placeBid() {
    // Skip calculations if there is no cash
    if (cash == 0) {
      return 0;
    }

    double median = new Median().evaluate(history.stream().mapToDouble(d -> d).toArray());
    int nextValue = (int) (Math.round(median)) + 1;
    return nextValue <= cash ? nextValue : 0;
  }

  /**
   * @throws IllegalArgumentException if own or other are negative numbers
   */
  @Override
  public void bids(int own, int other) {
    Assert.isTrue(own >= 0, "Own bid must be a positive number");
    Assert.isTrue(other >= 0, "Other bid must be a positive number");

    cash -= own;
    history.add(WinFunctions.findWinnerBid(own, other));
  }
}
