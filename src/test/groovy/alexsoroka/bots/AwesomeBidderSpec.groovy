package alexsoroka.bots

import spock.lang.Specification
import spock.lang.Unroll

class AwesomeBidderSpec extends Specification {

  AwesomeBidder cut

  def setup() {
    cut = new AwesomeBidder()
  }

  @Unroll
  def 'init should not fail when (quantity=#quantity, cash=#cash)'() {
    when:
    cut.init(quantity, cash)

    then:
    noExceptionThrown()

    where:
    quantity | cash
    20       | 15
    6        | 4
    0        | 0
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
    1        | 10   || IllegalArgumentException.class
    5        | 10   || IllegalArgumentException.class
  }

  @Unroll
  def 'placeBid should place bid=0 when bid is first, quantity=#quantity, cash=#cash'() {
    given:
    cut.init(quantity, cash)

    when:
    int result = cut.placeBid()

    then:
    result == bid

    where:
    quantity | cash || bid
    0        | 0    || 0
    10       | 0    || 0
    0        | 10   || 0
  }


  @Unroll
  def 'placeBid should place bid=#bid1 or bid=#bid2 when bid is first, quantity=#quantity, cash=#cash'() {
    given:
    cut.init(quantity, cash)

    when:
    int result = cut.placeBid()

    then:
    result == bid1 || result == bid2

    where:
    quantity | cash || bid1 | bid2
    2        | 1    || 0    | 1
    2        | 1    || 0    | 1
    2        | 2    || 1    | 2
    100      | 10   || 1    | 2
    10       | 100  || 1    | 2
    100      | 100  || 1    | 2
    5356     | 1313 || 1    | 2
  }

  def 'placeBid should place only 1 as bid if opponent lost his cash'() {
    given:
    cut.init(10, 10)
    cut.bids(1, 10)

    when:
    def firstBid = cut.placeBid()
    cut.bids(firstBid, 0)

    def secondBid = cut.placeBid()
    cut.bids(secondBid, 0)

    def thirdBid = cut.placeBid()
    cut.bids(thirdBid, 0)

    then:
    firstBid == 1
    secondBid == 1
    thirdBid == 1
  }

  def 'placeBid should place opponent cash plus one when it is absolutely enough to win'() {
    given:
    cut.init(10, 10)
    cut.bids(1, 2)
    cut.bids(3, 7)

    when:
    def firstBid = cut.placeBid()
    cut.bids(firstBid, 0)

    def secondBid = cut.placeBid()
    cut.bids(secondBid, 0)

    def thirdBid = cut.placeBid()
    cut.bids(thirdBid, 1)

    then:
    firstBid == 2
    secondBid == 2
    thirdBid == 2
  }

  def 'placeBid should increment winner bid from previous round'() {
    given:
    cut.init(20, 10)
    cut.bids(1, 2)
    cut.bids(3, 4)

    when:
    def bid = cut.placeBid()

    then:
    bid == 5 || bid == 6
  }

  @Unroll
  def 'placeBid should place bid=#bid1 or bid=#bid2 when lastOwnBid=#lastOwnBid, lastOpponentBid=#lastOpponentBid, cash=#cash'() {
    given:
    cut.init(100, cash)
    cut.bids(lastOwnBid, lastOpponentBid)

    when:
    int result = cut.placeBid()

    then:
    result == bid1 || result == bid2

    where:
    lastOwnBid | lastOpponentBid | cash     || bid1 | bid2
    30         | 30              | 100 + 30 || 31   | 32
    31         | 30              | 100 + 31 || 32   | 33
    30         | 31              | 100 + 30 || 32   | 33
    48         | 40              | 50 + 48  || 50   | 49
    40         | 48              | 50 + 40  || 50   | 49
  }

  def 'placeBid should place random bid when generated bid is out of cash'() {
    given:
    cut.init(100, 50 + 30)
    cut.bids(30, 50)

    when:
    int result = cut.placeBid()

    then:
    result >= 0 && result <= 50
  }

  def 'placeBid should place small random bid after 4 big bids'() {
    given:
    cut.init(20, 100)
    cut.bids(1, 9)
    cut.bids(11, 8)
    cut.bids(13, 15)
    cut.bids(16, 1)
    cut.bids(17, 9)

    when:
    int bid = cut.placeBid()

    then:
    bid <= 5
  }

  def 'placeBid should not place small random bid after one big and one small bids'() {
    given:
    cut.init(20, 100)
    cut.bids(1, 11)
    cut.bids(13, 8)

    when:
    int bid = cut.placeBid()

    then:
    bid >= 13
  }

  @Unroll
  def 'bids should not fail when (own=#own, other=#other)'() {
    when:
    cut.bids(own, other)

    then:
    noExceptionThrown()

    where:
    own | other
    5   | 10
    0   | 11
    23  | 0
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
