package alexsoroka.auction;

import alexsoroka.bots.Bidder;
import lombok.val;
import alexsoroka.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Simple auction implementation with 2 bidders
 */
public class TwoBiddersAuction implements Auction {

  private final int initialProductsQuantity;
  private final List<Bidder> bidders = new ArrayList<>();
  private final WinFunction winFunction;

  public TwoBiddersAuction(int initialProductsQuantity, WinFunction winFunction) {
    Assert.isTrue(initialProductsQuantity >= 0, "Products quantity must be positive");
    Assert.isTrue(initialProductsQuantity % 2 == 0, "Products quantity must be even");
    Assert.nonNull(winFunction, "Win function can not be null");

    this.initialProductsQuantity = initialProductsQuantity;
    this.winFunction = winFunction;
  }

  @Override
  public void register(Bidder bidder, int cash) {
    Assert.nonNull(bidder, "Bidder must not be null");
    Assert.state(this.bidders.size() <= 2, "Maximum bidders count is 2");

    bidder.init(initialProductsQuantity, cash);
    this.bidders.add(bidder);
  }

  @Override
  public Map<Bidder, Integer> run() {
    // TODO simplify
    // TODO add logging
    Assert.state(this.bidders.size() == 2, "Bidders count must be 2 for the bet");

    Map<Bidder, Integer> statistics = new HashMap<>();
    val firstBidder = bidders.get(0);
    val secondBidder = bidders.get(1);
    statistics.put(firstBidder, 0);
    statistics.put(secondBidder, 0);

    IntStream.range(0, initialProductsQuantity / 2).forEach(index -> {
      int firstBid = firstBidder.placeBid();
      int secondBid = secondBidder.placeBid();

      firstBidder.bids(firstBid, secondBid);
      secondBidder.bids(secondBid, firstBid);

      WinFunction.BidResult bidResult = winFunction.apply(firstBid, secondBid);
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
}
