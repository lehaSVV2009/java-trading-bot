package alexsoroka.bots

import static alexsoroka.auction.AuctionsUtils.runTwoBiddersAuctions

import spock.lang.Specification
import spock.lang.Unroll

class BotsComparisonSpec extends Specification {

  @Unroll
  def 'random bot should win zero bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def randomBidder = new RandomBidder()
    def zeroOnlyBidder = new ZeroOnlyBidder()

    when:
    def statistics = runTwoBiddersAuctions(randomBidder, zeroOnlyBidder, quantity, cash, iterations)

    then:
    statistics[randomBidder].victories > statistics[zeroOnlyBidder].victories

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

  @Unroll
  def 'plusOne bot should win zero bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneBidder = new PlusOneBidder()
    def zeroOnlyBidder = new ZeroOnlyBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, zeroOnlyBidder, quantity, cash, iterations)

    then:
    statistics[plusOneBidder].victories > statistics[zeroOnlyBidder].victories

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

  @Unroll
  def 'plusOne bot should win random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneBidder = new PlusOneBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[plusOneBidder].victories > statistics[randomBidder].victories

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
