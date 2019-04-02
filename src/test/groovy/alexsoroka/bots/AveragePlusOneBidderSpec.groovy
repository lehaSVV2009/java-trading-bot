package alexsoroka.bots

import spock.lang.Specification
import spock.lang.Unroll

class AveragePlusOneBidderSpec extends Specification {

  @Unroll
  def 'init should throw #exception when (quantity=#quantity, cash=#cash)'() {
    when:
    new AveragePlusOneBidder().init(quantity, cash)

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
    def bidder = new AveragePlusOneBidder()
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
  def 'placeBid should return bid=#bid when bidsHistory=#bidsHistory and cash=#cash'() {
    given:
    def bidder = new AveragePlusOneBidder()
    bidder.init(0, cash)
    bidsHistory.each {
      bidder.bids(it[0], it[1])
    }

    when:
    int result = bidder.placeBid()

    then:
    result == bid

    where:
    bidsHistory                    | cash || bid
    []                             | 1000 || 1
    [[0, 0]]                       | 1000 || 1
    [[0, 1]]                       | 1000 || 2
    [[60, 40]]                     | 1000 || 51
    [[100, 100], [100, 100]]       | 1000 || 101
    [[10, 20], [30, 40]]           | 1000 || ((10 + 20 + 30 + 40) / 4) + 1
    [[25, 26], [23, 20], [21, 22]] | 1000 || ((26 + 26 + 23 + 20 + 21 + 22) / 6) + 1
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
