package alexsoroka.auction;

import static alexsoroka.bots.WinFunctions.findBidResult;

import alexsoroka.bots.BidResult;
import alexsoroka.bots.Bidder;
import alexsoroka.util.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

// TODO try to refactor and move to separate files
@Slf4j
public class FirstPriceSealedBidAuctionsRunner {

  /**
   * Run n auctions with 2 bidders
   *
   * @param firstBidder  first bidder algorithm
   * @param secondBidder second bidder algorithm
   * @param quantity     products amount at the start of auction
   * @param cash         initial amount of money for both bidders
   * @param iterations   amount of auctions to run
   * @return bidder <-> bidder purchases amount
   */
  public static Map<Bidder, BidderStatistics> runTwoBiddersAuctions(
      Bidder firstBidder,
      Bidder secondBidder,
      int quantity,
      int cash,
      int iterations
  ) {
    Assert.nonNull(firstBidder, "First bidder must not be null");
    Assert.nonNull(secondBidder, "Second bidder must not be null");
    Assert.isTrue(quantity >= 0, "Products quantity must be positive");
    Assert.isTrue(quantity % 2 == 0, "Products quantity must be even");
    Assert.isTrue(cash >= 0, "Cash must be positive");
    Assert.isTrue(iterations >= 0, "Iterations argument must be even");

    log.debug("[{}] iterations of [{}] vs [{}] with quantity [{}] and cash [{}] has started.",
        iterations, firstBidder.getClass(), secondBidder.getClass(), quantity, cash
    );

    Map<Bidder, BidderStatistics> result = new HashMap<>();
    result.put(firstBidder, new BidderStatistics());
    result.put(secondBidder, new BidderStatistics());

    IntStream.range(0, iterations).forEach(index -> {
      Map<Bidder, Integer> bidderPurchasesMap = runTwoBiddersAuction(firstBidder, secondBidder, quantity, cash);

      int firstBidderPurchases = bidderPurchasesMap.get(firstBidder);
      int secondBidderPurchases = bidderPurchasesMap.get(secondBidder);

      if (firstBidderPurchases > secondBidderPurchases) {
        addVictoryToStatistics(result, firstBidder, secondBidder);
      } else if (firstBidderPurchases < secondBidderPurchases) {
        addVictoryToStatistics(result, secondBidder, firstBidder);
      } else {
        addDrawToStatistics(result, firstBidder, secondBidder);
      }
    });

    log.debug("[{}] has [{}] and [{}] has [{}] after [{}] iterations with quantity [{}] and cash [{}].",
        firstBidder.getClass(), result.get(firstBidder), secondBidder.getClass(), result.get(secondBidder), iterations, quantity, cash
    );

    return result;
  }

  /**
   * Run auction with 2 bidders
   *
   * @param firstBidder             first bidder algorithm
   * @param secondBidder            second bidder algorithm
   * @param initialProductsQuantity products amount at the start
   * @param cash                    initial amount of money for both bidders
   * @return bidder <-> bidder purchases amount
   */
  private static Map<Bidder, Integer> runTwoBiddersAuction(
      Bidder firstBidder,
      Bidder secondBidder,
      int initialProductsQuantity,
      int cash
  ) {

    Map<Bidder, Integer> statistics = new HashMap<>();
    statistics.put(firstBidder, 0);
    statistics.put(secondBidder, 0);

    // Init bidders
    firstBidder.init(initialProductsQuantity, cash);
    secondBidder.init(initialProductsQuantity, cash);

    IntStream.range(0, initialProductsQuantity / 2).forEach(index -> {
      // Make bids
      int firstBid = firstBidder.placeBid();
      int secondBid = secondBidder.placeBid();

      // Show bids
      firstBidder.bids(firstBid, secondBid);
      secondBidder.bids(secondBid, firstBid);

      // Calculate statistics
      BidResult bidResult = findBidResult(firstBid, secondBid);
      switch (bidResult) {
        case PLAYER_1_WIN: {
          statistics.put(firstBidder, statistics.get(firstBidder) + 2);
          break;
        }
        case PLAYER_2_WIN: {
          statistics.put(secondBidder, statistics.get(secondBidder) + 2);
          break;
        }
        case DRAW: {
          statistics.put(firstBidder, statistics.get(firstBidder) + 1);
          statistics.put(secondBidder, statistics.get(secondBidder) + 1);
          break;
        }
      }
    });

    return statistics;
  }

  private static void addVictoryToStatistics(
      Map<Bidder, BidderStatistics> statistics,
      Bidder winner,
      Bidder loser
  ) {
    statistics.put(winner, statistics.get(winner).addVictory());
    statistics.put(loser, statistics.get(loser).addLoss());
  }

  private static void addDrawToStatistics(
      Map<Bidder, BidderStatistics> statistics,
      Bidder firstBidder,
      Bidder secondBidder
  ) {
    statistics.put(firstBidder, statistics.get(firstBidder).addDraw());
    statistics.put(secondBidder, statistics.get(secondBidder).addDraw());
  }
}
