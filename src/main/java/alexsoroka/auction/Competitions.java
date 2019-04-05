package alexsoroka.auction;

import alexsoroka.bots.Bidder;
import alexsoroka.util.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
public class Competitions {

  /**
   * Run n auctions with 2 bidders
   *
   * @param firstBidder  first bidder algorithm
   * @param secondBidder second bidder algorithm
   * @param quantity     products amount at the start of auction
   * @param cash         initial amount of money for both bidders
   * @param iterations   amount of auctions to run
   * @return bidder <-> bidder statistics after n auctions
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

    log.info("[{}] iterations of [{}] vs [{}] with quantity [{}] and cash [{}] has started.",
        iterations, firstBidder.getClass(), secondBidder.getClass(), quantity, cash
    );

    Map<Bidder, BidderStatistics> result = new HashMap<>();
    result.put(firstBidder, new BidderStatistics());
    result.put(secondBidder, new BidderStatistics());

    IntStream.range(0, iterations).forEach(index -> {
      Auction auction = new FirstPriceSealedBidAuction(firstBidder, secondBidder, quantity, cash);
      Map<Bidder, BidderAuctionResult> bidderPurchasesMap = auction.run();

      BidderAuctionResult firstBidderResult = bidderPurchasesMap.get(firstBidder);
      BidderAuctionResult secondBidderResult = bidderPurchasesMap.get(secondBidder);

      int comparison = firstBidderResult.compareTo(secondBidderResult);
      if (comparison > 0) {
        addVictoryToStatistics(result, firstBidder, secondBidder);
      } else if (comparison < 0) {
        addVictoryToStatistics(result, secondBidder, firstBidder);
      } else {
        addDrawToStatistics(result, firstBidder, secondBidder);
      }
    });

    log.info("[{}] has [{}] and [{}] has [{}] after [{}] iterations with quantity [{}] and cash [{}].",
        firstBidder.getClass(), result.get(firstBidder), secondBidder.getClass(), result.get(secondBidder), iterations, quantity, cash
    );

    return result;
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
