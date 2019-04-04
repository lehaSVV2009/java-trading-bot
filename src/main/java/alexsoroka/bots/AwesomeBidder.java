package alexsoroka.bots;

import static alexsoroka.bots.BidResult.DRAW;
import static alexsoroka.bots.BidResult.PLAYER_1_WIN;
import static java.util.stream.Collectors.toList;

import alexsoroka.util.Assert;
import alexsoroka.util.Pair;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;


/**
 * Complex bidder with combinations of algorithms.
 */
public class AwesomeBidder implements Bidder {

  /**
   * Recent 10 own <-> other bids
   */
  private final CircularFifoQueue<Pair<Integer, Integer>> history = new CircularFifoQueue<>(10);

  /**
   * Randomizer instance
   */
  private final Random random = new Random();

  /**
   * Initial number of products to sell.
   */
  private int initialQuantity;

  /**
   * Current value of bidder money. 0 by default.
   */
  private int ownCash;

  /**
   * Current value of opponent money. 0 by default.
   */
  private int opponentCash;

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

  /**
   * @throws IllegalArgumentException if quantity or ownCash are negative numbers
   */
  @Override
  public void init(int quantity, int cash) {
    Assert.isTrue(quantity >= 0, "Quantity must be a positive number");
    Assert.isTrue(quantity % 2 == 0, "Quantity must be even");
    Assert.isTrue(cash >= 0, "Cash must be a positive number");

    this.initialQuantity = quantity;
    this.ownCash = cash;
    this.opponentCash = cash;
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
    // (same number algorithm)
    // (plus x opponent)
    // (plus x winner)
    // (average plus x)
    // (average winner plus x)
    // (average opponent plus x)

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

  /**
   * @throws IllegalArgumentException if own or other are negative numbers
   */
  @Override
  public void bids(int own, int other) {
    Assert.isTrue(own >= 0, "Own bid must be a positive number");
    Assert.isTrue(other >= 0, "Other bid must be a positive number");

    ownCash -= own;
    opponentCash -= other;

    history.add(Pair.of(own, other));

    BidResult bidResult = WinFunctions.findBidResult(own, other);
    if (PLAYER_1_WIN.equals(bidResult)) {
      ownPurchasesQuantity += 2;
    } else if (DRAW.equals(bidResult)) {
      ownPurchasesQuantity++;
    }
  }

  private int calculateMedian(Queue<Pair<Integer, Integer>> history) {
    double[] bids = history.stream()
        .flatMap(pair -> Stream.of(pair.getLeft(), pair.getRight()))
        .mapToDouble(number -> number)
        .toArray();

    double median = new Median().evaluate(bids);
    return (int) (Math.round(median));
  }

  private long calculateMinimumTurnsToWin(int initialQuantity, int ownPurchasesQuantity) {
    int quantityToWin = 1 + (initialQuantity / 2) - ownPurchasesQuantity;
    return Math.round((double) quantityToWin / 2);
  }

  private boolean isRecentBidsTooLarge(Queue<Pair<Integer, Integer>> history, int initialAverageTurnBid) {
    return history.size() > 4
        && history
        .stream()
        .skip(history.size() - (random.nextInt(3) + 2))
        .allMatch(pair -> pair.getLeft() > initialAverageTurnBid);
  }

  private int randomIfGreaterThanCash(int bid, int cash) {
    return bid <= cash ? bid : random.nextInt(cash);
  }

  // TODO add to BasicAlgorithmDetector
  private boolean isOpponentPlusXAlgorithm(Queue<Pair<Integer, Integer>> history) {
    int historyLength = history.size();

    List<Integer> ownBids = history.stream().map(Pair::getLeft).collect(toList());
    List<Integer> oppositeBids = history.stream().map(Pair::getRight).collect(toList());

    Set<Integer> neighbourBidsDifferences = new HashSet<>();
    for (int index = 1; index < oppositeBids.size(); ++index) {
      if (neighbourBidsDifferences.size() > 2) {
        return false;
      }
      Integer oppositeBid = oppositeBids.get(index);
      Integer ownBid = ownBids.get(index - 1);
      neighbourBidsDifferences.add(oppositeBid - ownBid);
    }

    return neighbourBidsDifferences.size() == 1;
  }
}
