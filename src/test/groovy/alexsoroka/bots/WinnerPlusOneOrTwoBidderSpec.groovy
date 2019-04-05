package alexsoroka.bots

import spock.lang.Specification
import spock.lang.Unroll

class WinnerPlusOneOrTwoBidderSpec extends Specification {

  WinnerPlusOneOrTwoBidder cut

  def setup() {
    cut = new WinnerPlusOneOrTwoBidder()
  }

  @Unroll
  def 'init should throw #exception when (quantity=#quantity, cash=#cash)'() {
    when:
    cut.init(quantity, cash)

    then:
    thrown(IllegalArgumentException.class)

    where:
    quantity | cash || exception
    -1       | -5   || IllegalArgumentException.class
    6        | -1   || IllegalArgumentException.class
    -10      | 1    || IllegalArgumentException.class
  }

  @Unroll
  def 'placeBid should return bid=#bid1 or bid=#bid2 when bid is first and cash=#cash'() {
    given:
    cut.init(0, cash)

    when:
    int result = cut.placeBid()

    then:
    result == bid1 || result == bid2

    where:
    cash || bid1 | bid2
    50   || 1    | 2
    10   || 1    | 2
    2    || 1    | 2
    1    || 1    | 0
    0    || 0    | 0
  }

  @Unroll
  def 'placeBid should return bid=#bid1 or bid=#bid2 when lastOwnBid=#lastOwnBid, lastOpponentBid=#lastOpponentBid and cash=#cash'() {
    given:
    cut.init(0, cash)
    cut.bids(lastOwnBid, lastOpponentBid)

    when:
    int result = cut.placeBid()

    then:
    result == bid1 || result == bid2

    where:
    lastOwnBid | lastOpponentBid | cash     || bid1 | bid2
    0          | 0               | 0        || 0    | 0
    1          | 0               | 1 + 1    || 0    | 0
    0          | 1               | 1        || 0    | 0
    1          | 0               | 2 + 1    || 2    | 0
    0          | 1               | 2        || 2    | 0
    30         | 30              | 100 + 30 || 31   | 32
    31         | 30              | 100 + 31 || 32   | 33
    30         | 31              | 100 + 30 || 32   | 33
    49         | 40              | 50 + 49  || 50   | 0
    40         | 49              | 50 + 40  || 50   | 0
    50         | 40              | 50 + 50  || 0    | 0
    40         | 50              | 50 + 40  || 0    | 0
  }

  @Unroll
  def 'bids should throw #exception when (own=#own, other=#other)'() {
    when:
    cut.bids(own, other)

    then:
    thrown(IllegalArgumentException.class)

    where:
    own | other || exception
    -5  | -5    || IllegalArgumentException.class
    5   | -5    || IllegalArgumentException.class
    -5  | 5     || IllegalArgumentException.class
  }
}
