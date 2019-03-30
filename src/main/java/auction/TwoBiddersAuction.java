package auction;

import lombok.val;

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
    if (initialProductsQuantity < 0) {
      throw new IllegalArgumentException("Products quantity must be positive");
    }
    if (initialProductsQuantity % 2 == 1) {
      throw new IllegalArgumentException("Products quantity must be even");
    }
    if (winFunction == null) {
      throw new IllegalArgumentException("Win function can not be null");
    }
    this.initialProductsQuantity = initialProductsQuantity;
    this.winFunction = winFunction;
  }

  @Override
  public void register(Bidder bidder, int cash) {
    if (bidder == null) {
      throw new IllegalArgumentException("Bidder must not be null");
    }
    if (this.bidders.size() > 2) {
      throw new IllegalStateException("Maximum bidders count is 2");
    }
    bidder.init(initialProductsQuantity, cash);
    this.bidders.add(bidder);
  }

  @Override
  public Map<Bidder, Integer> run() {
    if (this.bidders.size() < 2) {
      throw new IllegalStateException("Bidders count must be 2 for the bet");
    }

    Map<Bidder, Integer> statistics = new HashMap<>();
    val firstBidder = bidders.get(0);
    val secondBidder = bidders.get(1);
    statistics.put(firstBidder, 0);
    statistics.put(secondBidder, 0);

    IntStream.range(0, initialProductsQuantity / 2).forEach(index -> {
      WinFunction.BidResult bidResult = winFunction.apply(
          firstBidder.placeBid(),
          secondBidder.placeBid()
      );

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
