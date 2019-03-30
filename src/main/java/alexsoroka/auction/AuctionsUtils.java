package alexsoroka.auction;

import alexsoroka.bots.Bidder;
import lombok.val;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class AuctionsUtils {

  public static Map<Bidder, BidderStatistics> runTwoBiddersAuctions(
      Bidder firstBidder,
      Bidder secondBidder,
      int quantity,
      int cash,
      int iterations
  ) {
    // TODO refactor this awful code!!!
    Map<Bidder, BidderStatistics> result = new HashMap<>();
    result.put(firstBidder, new BidderStatistics());
    result.put(secondBidder, new BidderStatistics());

    WinFunction winFunction = new WinFunction();
    IntStream.range(0, iterations).forEach(index -> {
      val auction = new TwoBiddersAuction(quantity, winFunction);
      auction.register(firstBidder, cash);
      auction.register(secondBidder, cash);
      Map<Bidder, Integer> bidderPurchasesMap = auction.run();

      int firstBidderPurchases = bidderPurchasesMap.get(firstBidder);
      int secondBidderPurchases = bidderPurchasesMap.get(secondBidder);

      if (firstBidderPurchases > secondBidderPurchases) {
        addVictoryToStats(result, firstBidder, secondBidder);
      } else if (firstBidderPurchases < secondBidderPurchases) {
        addVictoryToStats(result, secondBidder, firstBidder);
      } else {
        addDrawToStats(result, firstBidder, secondBidder);
      }
    });

    return result;
  }

  private static void addVictoryToStats(
      Map<Bidder, BidderStatistics> statistics,
      Bidder winner,
      Bidder loser
  ) {
    statistics.put(winner, statistics.get(winner).addVictory());
    statistics.put(loser, statistics.get(loser).addLoss());
  }

  private static void addDrawToStats(
      Map<Bidder, BidderStatistics> statistics,
      Bidder firstBidder,
      Bidder secondBidder
  ) {
    statistics.put(firstBidder, statistics.get(firstBidder).addDraw());
    statistics.put(secondBidder, statistics.get(secondBidder).addDraw());
  }
}
