package alexsoroka.competitions

import static alexsoroka.auction.Competitions.runTwoBiddersAuctions

import alexsoroka.bots.AwesomeBidder
import alexsoroka.bots.HistoryMeanPlusOneBidder
import alexsoroka.bots.MedianPlusOneBidder
import alexsoroka.bots.OpponentPlusOneBidder
import alexsoroka.bots.OpponentPlusOneOrTwoBidder
import alexsoroka.bots.WinnerMedianPlusOneBidder
import alexsoroka.bots.WinnerPlusOneOrTwoBidder
import spock.lang.Specification
import spock.lang.Unroll

class VersusWinnerPlusOneOrTwoBidderCompetitions extends Specification {

  @Unroll
  def 'plusOne bot vs winnerPlusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneBidder = new OpponentPlusOneBidder()
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, winnerPlusOneOrTwoBidder, quantity, cash, iterations)

    then:
    statistics[plusOneBidder].victories > statistics[winnerPlusOneOrTwoBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // loose
    10       | 1000    | 100 // loose
    10       | 1000000 | 100 // loose
    1000     | 10      | 100 // loose
    1000     | 1000    | 100 // loose
    1000     | 1000000 | 100 // loose
    1000000  | 10      | 100 // loose
    1000000  | 1000    | 100 // loose
    1000000  | 1000000 | 100 // loose
  }

  @Unroll
  def 'plusOneOrTwo bot vs winnerPlusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneOrTwoBidder = new OpponentPlusOneOrTwoBidder()
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneOrTwoBidder, winnerPlusOneOrTwoBidder, quantity, cash, iterations)

    then:
    statistics[plusOneOrTwoBidder].victories > statistics[winnerPlusOneOrTwoBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // loose
    10       | 1000    | 100 // loose
    10       | 1000000 | 100 // loose
    1000     | 10      | 100 // loose
    1000     | 1000    | 100 // loose
    1000     | 1000000 | 100 // loose
    1000000  | 10      | 100 // loose
    1000000  | 1000    | 100 // loose
    1000000  | 1000000 | 100 // loose
  }

  @Unroll
  def 'history mean bot vs winnerPlusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def averageBidder = new HistoryMeanPlusOneBidder()
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(averageBidder, winnerPlusOneOrTwoBidder, quantity, cash, iterations)

    then:
    statistics[averageBidder].victories > statistics[winnerPlusOneOrTwoBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // loose
    10       | 1000    | 100 // loose
    10       | 1000000 | 100 // loose
    1000     | 10      | 100 // loose
    1000     | 1000    | 100 // loose
    1000     | 1000000 | 100 // loose
    1000000  | 10      | 100 // loose
    1000000  | 1000    | 100 // loose
    1000000  | 1000000 | 100
  }

  @Unroll
  def 'median bot vs winnerPlusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def medianBidder = new MedianPlusOneBidder()
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(medianBidder, winnerPlusOneOrTwoBidder, quantity, cash, iterations)

    then:
    statistics[medianBidder].victories > statistics[winnerPlusOneOrTwoBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // loose
    10       | 1000    | 100 // loose
    10       | 1000000 | 100 // loose
    1000     | 10      | 100 // loose
    1000     | 1000    | 100
    1000     | 1000000 | 100 // loose
    1000000  | 10      | 100 // loose
    1000000  | 1000    | 100 // loose
    1000000  | 1000000 | 100 // loose
  }

  @Unroll
  def 'winner median bot vs winnerPlusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerMedianBidder = new WinnerMedianPlusOneBidder()
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(winnerMedianBidder, winnerPlusOneOrTwoBidder, quantity, cash, iterations)

    then:
    statistics[winnerMedianBidder].victories > statistics[winnerPlusOneOrTwoBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // loose
    10       | 1000    | 100 // loose
    10       | 1000000 | 100 // loose
    1000     | 10      | 100 // loose
    1000     | 1000    | 100 // loose
    1000     | 1000000 | 100 // loose
    1000000  | 10      | 100 // loose
    1000000  | 1000    | 100 // loose
    1000000  | 1000000 | 100 // loose
  }

  @Unroll
  def 'awesome bot vs winnerPlusOneOrTwo bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def awesomeBidder = new AwesomeBidder()
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, winnerPlusOneOrTwoBidder, quantity, cash, iterations)

    then:
    statistics[awesomeBidder].victories > statistics[winnerPlusOneOrTwoBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100
    10       | 1000    | 100 // loose
    10       | 1000000 | 100 // loose
    1000     | 10      | 100 // can loose
    1000     | 1000    | 100
    1000     | 1000000 | 100 // can loose
    1000000  | 10      | 100
    1000000  | 1000    | 100
    1000000  | 1000000 | 100
  }
}
