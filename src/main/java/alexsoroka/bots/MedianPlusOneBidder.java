package alexsoroka.bots;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.Queue;


/**
 * Bidder with bids that bids median value from the recent history plus one.
 */
public class MedianPlusOneBidder extends AbstractBidder {

  /**
   * Recent 100 bids (own and opposite)
   */
  private Queue<Integer> history = new CircularFifoQueue<>(100);


  @Override
  protected void afterInit(int quantity, int cash) {
    this.history.clear();
  }

  /**
   * @return median bid plus one from recent 100 bids in the auction
   */
  @Override
  public int placeBid() {
    // Skip calculations if there is no cash
    if (ownCash == 0) {
      return 0;
    }

    double median = new Median().evaluate(history.stream().mapToDouble(d -> d).toArray());
    int nextValue = (int) (Math.round(median)) + 1;
    return zeroIfGreaterThanCash(nextValue, ownCash);
  }

  @Override
  protected void afterBids(int own, int other) {
    history.add(own);
    history.add(other);
  }
}
