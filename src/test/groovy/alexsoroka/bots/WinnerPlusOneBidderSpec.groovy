package alexsoroka.bots

import spock.lang.Specification
import spock.lang.Unroll

class WinnerPlusOneBidderSpec extends Specification {

  @Unroll
  def 'init should throw #exception when (quantity=#quantity, cash=#cash)'() {
    when:
    new WinnerPlusOneBidder().init(quantity, cash)

    then:
    thrown(IllegalArgumentException.class)

    where:
    quantity | cash || exception
    -1       | -5   || IllegalArgumentException.class
    6        | -1   || IllegalArgumentException.class
    -10      | 1    || IllegalArgumentException.class
  }

  @Unroll
  def 'placeBid should return bid=#bid when bid is first and cash=#cash'() {
    given:
    def bidder = new WinnerPlusOneBidder()
    bidder.init(0, cash)

    when:
    int result = bidder.placeBid()

    then:
    result == bid

    where:
    cash || bid
    50   || 1
    10   || 1
    1    || 1
    0    || 0
  }

  @Unroll
  def 'placeBid should return bid=#bid when lastOwnBid=#lastOwnBid, lastOpponentBid=#lastOpponentBid and cash=#cash'() {
    given:
    def bidder = new WinnerPlusOneBidder()
    bidder.init(0, cash)
    bidder.bids(lastOwnBid, lastOpponentBid)

    when:
    int result = bidder.placeBid()

    then:
    result == bid

    where:
    lastOwnBid | lastOpponentBid | cash     || bid
    0          | 0               | 0        || 0
    1          | 0               | 1 + 1    || 0
    0          | 1               | 1        || 0
    1          | 0               | 2 + 1    || 2
    0          | 1               | 2        || 2
    30         | 30              | 100 + 30 || 31
    31         | 30              | 100 + 31 || 32
    30         | 31              | 100 + 30 || 32
    49         | 40              | 50 + 49  || 50
    40         | 49              | 50 + 40  || 50
    50         | 40              | 50 + 50  || 0
    40         | 50              | 50 + 40  || 0
  }

  @Unroll
  def 'bids should throw #exception when (own=#own, other=#other)'() {
    when:
    new RandomBidder().bids(own, other)

    then:
    thrown(IllegalArgumentException.class)

    where:
    own | other || exception
    -5  | -5    || IllegalArgumentException.class
    5   | -5    || IllegalArgumentException.class
    -5  | 5     || IllegalArgumentException.class
  }
}
