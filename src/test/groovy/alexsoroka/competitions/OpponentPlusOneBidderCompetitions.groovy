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
import alexsoroka.bots.WinnerMedianPlusOneBidder
import alexsoroka.bots.WinnerPlusOneOrTwoBidder
import spock.lang.Specification
import spock.lang.Unroll

class OpponentPlusOneBidderCompetitions extends Specification {
  OpponentPlusOneBidder plusOneBidder

  def setup() {
    plusOneBidder = new OpponentPlusOneBidder()
  }

  @Unroll
  def 'should #prediction plusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneOrTwoBidder = new OpponentPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, plusOneOrTwoBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[plusOneBidder], statistics[plusOneOrTwoBidder])

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
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction winnerPlusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, winnerPlusOneOrTwoBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[plusOneBidder], statistics[winnerPlusOneOrTwoBidder])

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

  @Unroll
  def 'should #prediction historyMean bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def historyMeanBidder = new HistoryMeanPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, historyMeanBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[plusOneBidder], statistics[historyMeanBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN_OR_LOOSE
    10       | 1000    | 100        || WIN
    10       | 1000000 | 100        || WIN
    1000     | 10      | 100        || WIN_OR_LOOSE
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN_OR_LOOSE
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction median bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def medianBidder = new MedianPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, medianBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[plusOneBidder], statistics[medianBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN_OR_LOOSE
    10       | 1000    | 100        || WIN
    10       | 1000000 | 100        || WIN
    1000     | 10      | 100        || WIN_OR_LOOSE
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN_OR_LOOSE
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction winnerMedian bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerMedianBidder = new WinnerMedianPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, winnerMedianBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[plusOneBidder], statistics[winnerMedianBidder])

    where:
    quantity | cash    | iterations || prediction
    10       | 10      | 100        || WIN_OR_LOOSE
    10       | 1000    | 100        || WIN
    10       | 1000000 | 100        || WIN
    1000     | 10      | 100        || WIN_OR_LOOSE
    1000     | 1000    | 100        || WIN
    1000     | 1000000 | 100        || WIN
    1000000  | 10      | 100        || WIN_OR_LOOSE
    1000000  | 1000    | 100        || WIN
    1000000  | 1000000 | 100        || WIN
  }

  @Unroll
  def 'should #prediction awesome bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def awesomeBidder = new AwesomeBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, awesomeBidder, quantity, cash, iterations)

    then:
    assertBidderStatistics(prediction, statistics[plusOneBidder], statistics[awesomeBidder])

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
    1000000  | 1000000 | 100        || WIN
  }

}
