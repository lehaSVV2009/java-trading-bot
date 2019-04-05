package alexsoroka.competitions

import static alexsoroka.auction.Competitions.runTwoBiddersAuctions

import alexsoroka.bots.AwesomeBidder
import alexsoroka.bots.OpponentPlusOneBidder
import alexsoroka.bots.RandomBidder
import alexsoroka.bots.ZeroOnlyBidder
import spock.lang.Specification
import spock.lang.Unroll

class VersusZeroOnlyBidderCompetitions extends Specification {

  @Unroll
  def 'random bot vs zero bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
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
  def 'plusOne bot vs zero bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneBidder = new OpponentPlusOneBidder()
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
  def 'awesome bot vs zero bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def awesomeBidder = new AwesomeBidder()
    def zeroOnlyBidder = new ZeroOnlyBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, zeroOnlyBidder, quantity, cash, iterations)

    then:
    statistics[awesomeBidder].victories > statistics[zeroOnlyBidder].victories

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
