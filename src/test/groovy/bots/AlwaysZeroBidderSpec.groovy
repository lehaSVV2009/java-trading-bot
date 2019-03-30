package bots

import spock.lang.Specification
import spock.lang.Unroll

class AlwaysZeroBidderSpec extends Specification {

  @Unroll
  def 'placeBid should return 0 when cash=#cash and quantity=#quantity'() {
    given:
    def bidder = new AlwaysZeroBidder()
    bidder.init(quantity, cash)

    when:
    int bid = bidder.placeBid()

    then:
    bid == 0

    where:
    cash | quantity
    0    | 0
    10   | 0
    1000 | 0
    0    | 100
    10   | 100
    -1   | -1
  }
}
