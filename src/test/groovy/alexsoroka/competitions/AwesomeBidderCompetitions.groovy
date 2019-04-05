package alexsoroka.competitions

import static alexsoroka.auction.Competitions.runTwoBiddersAuctions
import static alexsoroka.competitions.CompetitionPrediction.LOOSE
import static alexsoroka.competitions.CompetitionPrediction.WIN
import static alexsoroka.competitions.CompetitionPrediction.WIN_OR_LOOSE
import static alexsoroka.competitions.CompetitionPrediction.assertBidderStatistics

import alexsoroka.bots.HistoryMeanPlusOneBidder
import alexsoroka.bots.AwesomeBidder
import alexsoroka.bots.MedianPlusOneBidder
import alexsoroka.bots.OpponentPlusOneBidder
import alexsoroka.bots.OpponentPlusOneOrTwoBidder
import alexsoroka.bots.RandomBidder
import alexsoroka.bots.WinnerMedianPlusOneBidder
import alexsoroka.bots.WinnerPlusOneOrTwoBidder
import spock.lang.Specification
import spock.lang.Unroll

class AwesomeBidderCompetitions extends Specification {
  AwesomeBidder awesomeBidder

  def setup() {
    awesomeBidder = new AwesomeBidder()
  }

  @Unroll
  def 'should #prediction random bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, randomBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[awesomeBidder], statistics[randomBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN
    10       | 1000    | 100        || WIN
    10       | 1000000 | 100        || WIN
    1000     | 10      | 100        || WIN
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneBidder = new OpponentPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, plusOneBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[awesomeBidder], statistics[plusOneBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN
    10       | 1000    | 100        || WIN
    10       | 1000000 | 100        || WIN
    1000     | 10      | 100        || WIN
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || LOOSE
  }

  @Unroll
  def 'should #prediction plusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneOrTwoBidder = new OpponentPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, plusOneOrTwoBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[awesomeBidder], statistics[plusOneOrTwoBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN_OR_LOOSE
    10       | 1000    | 100        || WIN_OR_LOOSE
    10       | 1000000 | 100        || WIN_OR_LOOSE
    1000     | 10      | 100        || WIN
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction winnerPlusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, winnerPlusOneOrTwoBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[awesomeBidder], statistics[winnerPlusOneOrTwoBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN
    10       | 1000    | 100        || LOOSE
    10       | 1000000 | 100        || LOOSE
    1000     | 10      | 100        || WIN_OR_LOOSE
    1000     | 1000    | 100        || WIN_OR_LOOSE
    1000     | 1000000 | 100        || WIN_OR_LOOSE
    1000000  | 10      | 100        || WIN_OR_LOOSE
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction historyMean bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def historyMeanBidder = new HistoryMeanPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, historyMeanBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[awesomeBidder], statistics[historyMeanBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN
    10       | 1000    | 100        || WIN
    10       | 1000000 | 100        || WIN
    1000     | 10      | 100        || WIN
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction median bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def medianBidder = new MedianPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, medianBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[awesomeBidder], statistics[medianBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN
    10       | 1000    | 100        || WIN
    10       | 1000000 | 100        || WIN
    1000     | 10      | 100        || WIN
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction winnerMedian bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerMedianBidder = new WinnerMedianPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, winnerMedianBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[awesomeBidder], statistics[winnerMedianBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN
    10       | 1000    | 100        || WIN
    10       | 1000000 | 100        || WIN
    1000     | 10      | 100        || WIN
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }
}
