package alexsoroka.competitions

import static alexsoroka.auction.Competitions.runTwoBiddersAuctions

import alexsoroka.bots.HistoryMeanPlusOneBidder
import alexsoroka.bots.AwesomeBidder
import alexsoroka.bots.MedianPlusOneBidder
import alexsoroka.bots.OpponentPlusOneBidder
import alexsoroka.bots.OpponentPlusTwoBidder
import alexsoroka.bots.WinnerMedianPlusOneBidder
import alexsoroka.bots.WinnerPlusOneOrTwoBidder
import spock.lang.Specification
import spock.lang.Unroll

class VersusAwesomeBidderCompetitions extends Specification {

  @Unroll
  def 'plusOne bot vs awesome bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneBidder = new OpponentPlusOneBidder()
    def awesomeBidder = new AwesomeBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, awesomeBidder, quantity, cash, iterations)

    then:
    statistics[plusOneBidder].victories > statistics[awesomeBidder].victories

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
  def 'plusOneOrTwo bot vs awesome bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneOrTwoBidder = new OpponentPlusTwoBidder()
    def awesomeBidder = new AwesomeBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneOrTwoBidder, awesomeBidder, quantity, cash, iterations)

    then:
    statistics[plusOneOrTwoBidder].victories > statistics[awesomeBidder].victories

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
  def 'winnerPlusOneOrTwo bot vs awesome bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()
    def awesomeBidder = new AwesomeBidder()

    when:
    def statistics = runTwoBiddersAuctions(winnerPlusOneOrTwoBidder, awesomeBidder, quantity, cash, iterations)

    then:
    statistics[winnerPlusOneOrTwoBidder].victories > statistics[awesomeBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // can loose
    10       | 1000    | 100
    10       | 1000000 | 100
    1000     | 10      | 100 // can loose
    1000     | 1000    | 100 // can loose
    1000     | 1000000 | 100 // can loose
    1000000  | 10      | 100 // can loose
    1000000  | 1000    | 100 // can loose
    1000000  | 1000000 | 100 // can loose
  }

  @Unroll
  def 'history mean bot vs awesome bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def averageBidder = new HistoryMeanPlusOneBidder()
    def awesomeBidder = new AwesomeBidder()

    when:
    def statistics = runTwoBiddersAuctions(averageBidder, awesomeBidder, quantity, cash, iterations)

    then:
    statistics[averageBidder].victories > statistics[awesomeBidder].victories

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
  def 'median bot vs awesome bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def medianBidder = new MedianPlusOneBidder()
    def awesomeBidder = new AwesomeBidder()

    when:
    def statistics = runTwoBiddersAuctions(medianBidder, awesomeBidder, quantity, cash, iterations)

    then:
    statistics[medianBidder].victories > statistics[awesomeBidder].victories

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
  def 'winner median bot vs awesome bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerMedianBidder = new WinnerMedianPlusOneBidder()
    def awesomeBidder = new AwesomeBidder()

    when:
    def statistics = runTwoBiddersAuctions(winnerMedianBidder, awesomeBidder, quantity, cash, iterations)

    then:
    statistics[winnerMedianBidder].victories > statistics[awesomeBidder].victories

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
}
