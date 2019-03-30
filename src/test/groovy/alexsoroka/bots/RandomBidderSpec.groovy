package alexsoroka.bots

import spock.lang.Specification
import spock.lang.Unroll

class RandomBidderSpec extends Specification {

  @Unroll
  def 'init should throw #exception when (quantity=#quantity, cash=#cash)'() {
    when:
    new RandomBidder().init(quantity, cash)

    then:
    thrown(IllegalArgumentException.class)

    where:
    quantity | cash || exception
    -1       | -5   || IllegalArgumentException.class
    6        | -1   || IllegalArgumentException.class
    -10      | 1    || IllegalArgumentException.class
  }

  @Unroll
  def 'placeBid should generate random value within cash=#cash'() {
    given:
    def bidder = new RandomBidder()
    bidder.init(0, cash)

    when:
    int bid = bidder.placeBid()

    then:
    bid >= 0 && bid <= cash

    where:
    cash   | _
    0      | _
    10     | _
    25     | _
    100    | _
    1000   | _
    100000 | _
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
