package alexsoroka.auction

import spock.lang.Specification
import spock.lang.Unroll

class BidderAuctionResultSpec extends Specification {

  @Unroll
  def 'compare should return #expected when firstResult=#firstResult and secondResult=#secondResult'() {
    when:
    int result = firstResult.compareTo(secondResult)

    then:
    result == expected

    where:
    firstResult                     | secondResult                    || expected
    BidderAuctionResult.of(100, 0)  | BidderAuctionResult.of(10, 0)   || 1
    BidderAuctionResult.of(30, 0)   | BidderAuctionResult.of(100, 0)  || -1
    BidderAuctionResult.of(100, 40) | BidderAuctionResult.of(10, 60)  || 1
    BidderAuctionResult.of(30, 60)  | BidderAuctionResult.of(100, 40) || -1
    BidderAuctionResult.of(100, 60) | BidderAuctionResult.of(100, 40) || 1
    BidderAuctionResult.of(100, 40) | BidderAuctionResult.of(100, 60) || -1
    BidderAuctionResult.of(100, 50) | BidderAuctionResult.of(100, 50) || 0
  }
}
