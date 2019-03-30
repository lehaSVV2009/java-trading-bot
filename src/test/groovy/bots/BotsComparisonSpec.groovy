package bots

import static auction.AuctionsUtils.*

import spock.lang.Specification
import spock.lang.Unroll

class BotsComparisonSpec extends Specification {

  @Unroll
  def 'random bot should win zero bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def randomBidder = new RandomBidder()
    def alwaysZeroBot = new AlwaysZeroBidder()

    when:
    def statistics = runTwoBiddersAuctions(randomBidder, alwaysZeroBot, quantity, cash, iterations)

    then:
    statistics[randomBidder].victories > statistics[alwaysZeroBot].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100
    10       | 1000    | 100
    10       | 1000000 | 100
    1000     | 10      | 100
    1000     | 1000    | 100
    1000     | 1000000 | 100
    1000000  | 10      | 100
    1000000  | 1000    | 100
    1000000  | 1000000 | 100
  }
}
