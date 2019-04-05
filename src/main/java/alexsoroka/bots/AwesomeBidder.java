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
   * @return the average bid from all the bids in the auction
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

    // First bid is always small to look at the opponent's strategy
    if (history.size() == 0) {
      int firstBid = random.nextBoolean() ? 1 : 2;
      return randomIfGreaterThanCash(firstBid, ownCash);
    }

    // Check if it is possible to win by placing opponent's cash + 1 (when opponent's cash is too small)
    long minimumTurnsToWin = calculateMinimumTurnsToWin(initialQuantity, ownPurchasesQuantity);
    if (minimumTurnsToWin > 0 && ownCash >= (opponentCash + 1) * minimumTurnsToWin) {
      return opponentCash + 1;
    }

    // According to statistics median + 2 bidder wins even random algorithm on small quantity
    if (initialQuantity <= 10) {
      int median = calculateMedian(history);
      return randomIfGreaterThanCash(median + 2, ownCash);
    }

    // TODO detect basic algorithm by 10 previous turns
    // and place a winning bid for the algorithm
    // (same number algorithm, plus x opponent, plus x winner, average plus x, average winner plus x, average opponent plus x)

    // Workaround to not allow bot to make too big bids
    // If previous 2-4 rounds all the bids were bigger than average, place smaller one
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
    int quantityToWin = 1 + (initialQuantity / 2) - ownPurchasesQuantity;
    return Math.round((double) quantityToWin / 2);
  }

  /**
   * @return true if recent 2-4 own bids were bigger than initial arithmetic mean
   */
  private boolean isRecentBidsTooLarge(Queue<Pair<Integer, Integer>> history, int initialAverageTurnBid) {
    return history.size() > 4
        && history
        .stream()
        .skip(history.size() - (random.nextInt(3) + 2))
        .allMatch(pair -> pair.getLeft() > initialAverageTurnBid);
  }
}
