package alexsoroka.auction;

import static alexsoroka.bots.WinFunctions.findBidResult;

import alexsoroka.bots.BidResult;
import alexsoroka.bots.Bidder;
import alexsoroka.util.Assert;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Auction with only 2 bidders where next bids are hidden and bidder wins from the 1st bid
 */
@Slf4j
public class FirstPriceSealedBidAuction implements Auction {

  /**
   * First bidder algorithm
   */
  private final Bidder firstBidder;

  /**
   * Second bidder algorithm
   */
  private final Bidder secondBidder;

  /**
   * Products amount at the start
   */
  private final int initialProductsQuantity;

  /**
   * Initial amount of money for both bidders
   */
  private final int cash;


  public FirstPriceSealedBidAuction(Bidder firstBidder, Bidder secondBidder, int quantity, int cash) {
    Assert.nonNull(firstBidder, "First bidder must not be null");
    Assert.nonNull(secondBidder, "Second bidder must not be null");
    Assert.isTrue(quantity >= 0, "Products quantity must be positive");
    Assert.isTrue(quantity % 2 == 0, "Products quantity must be even");
    Assert.isTrue(cash >= 0, "Cash must be positive");

    this.firstBidder = firstBidder;
    this.secondBidder = secondBidder;
    this.initialProductsQuantity = quantity;
    this.cash = cash;
  }

  @Override
  public Map<Bidder, BidderAuctionResult> run() {
    log.debug("Auction of {} products with {} cash for {} and {} has been started",
        initialProductsQuantity, cash, firstBidder.getClass(), secondBidder.getClass()
    );

    val firstBidderResult = BidderAuctionResult.of(0, cash);
    val secondBidderResult = BidderAuctionResult.of(0, cash);

    // Init bidders
    firstBidder.init(initialProductsQuantity, cash);
    secondBidder.init(initialProductsQuantity, cash);

    IntStream.range(0, initialProductsQuantity / 2).forEach(index -> {
      // Place bids
      int firstBid = firstBidder.placeBid();
      int secondBid = secondBidder.placeBid();

      // Show bids
      firstBidder.bids(firstBid, secondBid);
      secondBidder.bids(secondBid, firstBid);

      // Calculate statistics
      firstBidderResult.deductCash(firstBid);
      secondBidderResult.deductCash(secondBid);
      BidResult bidResult = findBidResult(firstBid, secondBid);
      switch (bidResult) {
        case PLAYER_1_WIN: {
          firstBidderResult.addPurchase(2);
          break;
        }
        case PLAYER_2_WIN: {
          secondBidderResult.addPurchase(2);
          break;
        }
        default: {
          firstBidderResult.addPurchase(1);
          secondBidderResult.addPurchase(1);
          break;
        }
      }
    });

    Map<Bidder, BidderAuctionResult> statistics = new HashMap<>();
    statistics.put(firstBidder, firstBidderResult);
    statistics.put(secondBidder, secondBidderResult);

    log.debug("Auction of {} products with {} cash has finished with {} result for {} and {} result for {}",
        initialProductsQuantity, cash, firstBidderResult, firstBidder.getClass(), secondBidderResult, secondBidder.getClass()
    );
    return statistics;
  }
}
