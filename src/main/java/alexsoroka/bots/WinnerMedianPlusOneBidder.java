package alexsoroka.bots;

import alexsoroka.common.WinFunctions;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.Queue;


/**
 * Bidder with bids that bids median value from the recent winner history plus one.
 */
public class WinnerMedianPlusOneBidder extends AbstractBidder {

  /**
   * Recent 100 winner bids
   */
  private final Queue<Integer> history = new CircularFifoQueue<>(100);

  @Override
  protected void afterInit(int quantity, int cash) {
    this.history.clear();
  }

  /**
   * @return the median bid from winner bids in the auction history
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
    history.add(WinFunctions.findWinnerBid(own, other));
  }
}
