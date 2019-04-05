package alexsoroka.competitions

import static alexsoroka.auction.Competitions.runTwoBiddersAuctions
import static alexsoroka.competitions.CompetitionPrediction.LOOSE
import static alexsoroka.competitions.CompetitionPrediction.assertBidderStatistics

import alexsoroka.bots.RandomBidder
import alexsoroka.bots.ZeroOnlyBidder
import spock.lang.Specification
import spock.lang.Unroll

class ZeroOnlyBidderCompetitions extends Specification {

  ZeroOnlyBidder zeroOnlyBidder

  def setup() {
    zeroOnlyBidder = new ZeroOnlyBidder()
  }

  @Unroll
  def 'should #prediction random bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(zeroOnlyBidder, randomBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[zeroOnlyBidder], statistics[randomBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || LOOSE
    10       | 1000    | 100        || LOOSE
    10       | 1000000 | 100        || LOOSE
    1000     | 10      | 100        || LOOSE
    1000     | 1000    | 100        || LOOSE
    1000     | 1000000 | 100        || LOOSE
    1000000  | 10      | 100        || LOOSE
    1000000  | 1000    | 100        || LOOSE
    1000000  | 1000000 | 100        || LOOSE
  }

}
