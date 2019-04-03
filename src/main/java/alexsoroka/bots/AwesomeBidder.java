package alexsoroka.bots;

import static java.util.stream.Collectors.toList;

import alexsoroka.auction.BidResult;
import alexsoroka.auction.WinFunctions;
import alexsoroka.util.Assert;
import alexsoroka.util.Pair;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;


/**
 * Advanced awesome bidder.
 */
public class AwesomeBidder implements Bidder {

  /**
   * Recent 100 own <-> other bids
   */
  private final Queue<Pair<Integer, Integer>> history = new CircularFifoQueue<>(100);

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
   * Current number of products bought by bidder
   */
  private int ownPurchasesQuantity;

  /**
   * Current number of products bought by opponent bidder
   */
  private int opponentPurchasesQuantity;

  /**
   * Average price of single product from initial cash
   */
  private int averageProductPrice;

  /**
   * @throws IllegalArgumentException if quantity or ownCash are negative numbers
   */
  @Override
  public void init(int quantity, int cash) {
    Assert.isTrue(quantity >= 0, "Quantity must be a positive number");
    Assert.isTrue(cash >= 0, "Cash must be a positive number");

    this.initialQuantity = quantity;
    this.ownCash = cash;
    this.opponentCash = cash;
    this.averageProductPrice = quantity == 0 ? 0 : (int) Math.round(((double) cash / quantity));
    this.history.clear();
  }

  /**
   * @return the average bid from all the bids in the auction
   */
  @Override
  public int placeBid() {
    // Skip calculations if there is no cash
    if (ownCash == 0 || initialQuantity == 0) {
      return 0;
    }

    // Average price for 2 products is the 1st bid
    if (history.size() == 0) {
      int averageTwoProductsPrice = averageProductPrice * 2;
      return averageTwoProductsPrice <= ownCash ? averageTwoProductsPrice : ownCash;
    }

    // If owner quantity is close to win and own cash is bigger than opponent cash
    // Place a bid that bigger than opponent cash
    if (isOneStepToWin(initialQuantity, ownPurchasesQuantity) && ownCash > opponentCash) {
      return opponentCash + 1;
    }

    // According to analysis median bidder wins even random algorithm on small quantity
    if (initialQuantity <= 10) {
      int median = calculateMedian(history) + 1;
      return median <= ownCash ? median : 0;
    }

    // TODO
    // detect basic algorithm by 8 previous steps
    // and place a winner bid for the algorithm
    // (plus x opponent)
    // (plus x winner)
    // (average plus x)
    // (average winner plus x)
    // (average opponent plus x)

    // detect strategy type
    // if aggressive (too many bids bigger than average price)
    // TODO think about that

    // if patient (bids are less than average price)
    // do aggressive

    // if unknown
    // try either random small-small-big-big-too-small approach, that close to average price
    // or my own neural network
    return 1;
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
    switch (bidResult) {
      case PLAYER_1_WIN: {
        ownPurchasesQuantity += 2;
        break;
      }
      case PLAYER_2_WIN: {
        opponentPurchasesQuantity += 2;
        break;
      }
      default: {
        ownPurchasesQuantity++;
        opponentPurchasesQuantity++;
        break;
      }
    }
  }

  private int calculateMedian(Queue<Pair<Integer, Integer>> history) {
    double[] bids = history.stream()
        .flatMap(pair -> Stream.of(pair.getLeft(), pair.getRight()))
        .mapToDouble(number -> number)
        .toArray();

    double median = new Median().evaluate(bids);
    return (int) (Math.round(median)) + 1;
  }

  private boolean isOneStepToWin(int initialQuantity, int ownPurchasesQuantity) {
    int halfOfInitialQuantity = initialQuantity / 2;
    return ownPurchasesQuantity == halfOfInitialQuantity
        || ownPurchasesQuantity - 1 == halfOfInitialQuantity;
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
