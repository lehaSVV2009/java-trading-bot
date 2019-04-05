package alexsoroka.bots;

import static alexsoroka.common.BidResult.DRAW;
import static alexsoroka.common.BidResult.PLAYER_1_WIN;

import alexsoroka.common.BidResult;
import alexsoroka.common.WinFunctions;
import alexsoroka.util.Pair;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.Queue;
import java.util.Random;
import java.util.stream.Stream;


/**
 * Complex bidder with combinations of algorithms.
 */
public class AwesomeBidder extends AbstractBidder {

  /**
   * Recent 10 own <-> other bids
   */
  private final CircularFifoQueue<Pair<Integer, Integer>> history = new CircularFifoQueue<>(10);

  /**
   * Randomizer instance
   */
  private final Random random = new Random();

  /**
   * Current number of products bought by bidder.
   */
  private int ownPurchasesQuantity;

  /**
   * Number of all turns at the beginning of the auction.
   */
  private int allTurnsCount;

  /**
   * Arithmetic mean bid from initial cash.
   */
  private int initialArithmeticMeanBid;

  @Override
  protected void afterInit(int quantity, int cash) {
    this.ownPurchasesQuantity = 0;
    this.allTurnsCount = quantity / 2;
    this.initialArithmeticMeanBid = allTurnsCount == 0 ? 0 : (int) Math.round(((double) cash / allTurnsCount));
    this.history.clear();
  }

  /**
   * @return bid calculated by combination of winner plus one or two, max-max-min and history median algorithms
   */
  @Override
  public int placeBid() {
    // Skip calculations if there is no cash or turns
    if (ownCash == 0 || allTurnsCount == 0) {
      return 0;
    }

    // Don't waste money if opponent's cash is 0
    if (opponentCash == 0) {
      return 1;
    }

    // Place maximum bid if there is only one round
    if (initialQuantity == 2) {
      return ownCash;
    }

    // Check if it is possible to win by placing opponent's cash + 1 (when opponent's cash is too small)
    long minimumTurnsToWin = calculateMinimumTurnsToWin(initialQuantity, ownPurchasesQuantity);
    if (minimumTurnsToWin > 0 && ownCash >= (opponentCash + 1) * minimumTurnsToWin) {
      return opponentCash + 1;
    }

    // First bid is always small to not waste all money from the start (the algorithm is aggressive)
    if (history.size() == 0) {
      int firstBid = random.nextBoolean() ? 1 : 2;
      return randomIfGreaterThanCash(firstBid, ownCash);
    }

    // According to statistics median plus two bidder wins even random algorithm on small quantity
    if (initialQuantity <= 10) {
      int median = calculateMedian(history);
      return randomIfGreaterThanCash(median + 2, ownCash);
    }

    // Not allow bot to make too many large bids
    // If previous 2-4 rounds all the bids were greater than initial mean, place smaller one
    if (isRecentBidsTooLarge(history, initialArithmeticMeanBid)) {
      int smallBidLimit = initialArithmeticMeanBid / 2;
      int smallBid = random.nextInt(smallBidLimit != 0 ? smallBidLimit : 1);
      return randomIfGreaterThanCash(smallBid, ownCash);
    }

    // Use winner plus one or two algorithm
    Pair<Integer, Integer> previousTurn = history.get(history.size() - 1);
    int previousWinnerBid = WinFunctions.findWinnerBid(previousTurn.getLeft(), previousTurn.getRight());
    int nextValue = previousWinnerBid + (random.nextBoolean() ? 1 : 2);
    return randomIfGreaterThanCash(nextValue, ownCash);
  }

  @Override
  public void afterBids(int own, int other) {
    history.add(Pair.of(own, other));

    BidResult bidResult = WinFunctions.findBidResult(own, other);
    if (PLAYER_1_WIN.equals(bidResult)) {
      ownPurchasesQuantity += 2;
    } else if (DRAW.equals(bidResult)) {
      ownPurchasesQuantity++;
    }
  }

  /**
   * @return median value from history of both (own and other)
   */
  private int calculateMedian(Queue<Pair<Integer, Integer>> history) {
    double[] bids = history.stream()
        .flatMap(pair -> Stream.of(pair.getLeft(), pair.getRight()))
        .mapToDouble(number -> number)
        .toArray();

    double median = new Median().evaluate(bids);
    return (int) (Math.round(median));
  }

  /**
   * @return minimum amount of turns to win
   */
  private long calculateMinimumTurnsToWin(int initialQuantity, int ownPurchasesQuantity) {
    int minimumQuantityToWin = 1 + (initialQuantity / 2) - ownPurchasesQuantity;
    return Math.round((double) minimumQuantityToWin / 2);
  }

  /**
   * @return true if recent 2-4 own bids were greater than initial arithmetic mean
   */
  private boolean isRecentBidsTooLarge(Queue<Pair<Integer, Integer>> history, int initialArithmeticMeanBid) {
    return history.size() > 4
        && history
        .stream()
        .skip(history.size() - (random.nextInt(3) + 2))
        .allMatch(pair -> pair.getLeft() > initialArithmeticMeanBid);
  }
}
