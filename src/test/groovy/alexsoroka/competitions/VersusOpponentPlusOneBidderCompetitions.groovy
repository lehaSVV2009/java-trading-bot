package alexsoroka.competitions

import static alexsoroka.auction.Competitions.runTwoBiddersAuctions

import alexsoroka.bots.HistoryMeanPlusOneBidder
import alexsoroka.bots.AwesomeBidder
import alexsoroka.bots.MedianPlusOneBidder
import alexsoroka.bots.OpponentPlusOneBidder
import alexsoroka.bots.OpponentPlusOneOrTwoBidder
import alexsoroka.bots.OpponentPlusTwoBidder
import alexsoroka.bots.WinnerMedianPlusOneBidder
import alexsoroka.bots.WinnerPlusOneOrTwoBidder
import spock.lang.Specification
import spock.lang.Unroll

class VersusOpponentPlusOneBidderCompetitions extends Specification{

  @Unroll
  def 'plusTwo bot vs plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusTwoBidder = new OpponentPlusTwoBidder()
    def plusOneBidder = new OpponentPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusTwoBidder, plusOneBidder, quantity, cash, iterations)

    then:
    statistics[plusTwoBidder].victories > statistics[plusOneBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100
    10       | 1000    | 100
    10       | 1000000 | 100
    1000     | 10      | 100 // loose
    1000     | 1000    | 100
    1000     | 1000000 | 100 // loose
    1000000  | 10      | 100 // loose
    1000000  | 1000    | 100
    1000000  | 1000000 | 100
  }

  @Unroll
  def 'plusOneOrTwo bot vs plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneOrTwoBidder = new OpponentPlusOneOrTwoBidder()
    def plusOneBidder = new OpponentPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneOrTwoBidder, plusOneBidder, quantity, cash, iterations)

    then:
    statistics[plusOneOrTwoBidder].victories > statistics[plusOneBidder].victories

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
    1000000  | 1000000 | 100 // loose
  }

  @Unroll
  def 'winnerPlusOneOrTwo bot vs plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerPlusOneBidder = new WinnerPlusOneOrTwoBidder()
    def plusOneBidder = new OpponentPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(winnerPlusOneBidder, plusOneBidder, quantity, cash, iterations)

    then:
    statistics[winnerPlusOneBidder].victories > statistics[plusOneBidder].victories

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
  def 'history mean bot vs plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def averageBidder = new HistoryMeanPlusOneBidder()
    def plusOneBidder = new OpponentPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(averageBidder, plusOneBidder, quantity, cash, iterations)

    then:
    statistics[averageBidder].victories > statistics[plusOneBidder].victories

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
  def 'median bot vs plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def medianBidder = new MedianPlusOneBidder()
    def plusOneBidder = new OpponentPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(medianBidder, plusOneBidder, quantity, cash, iterations)

    then:
    statistics[medianBidder].victories > statistics[plusOneBidder].victories

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
  def 'winner median bot vs plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def medianBidder = new WinnerMedianPlusOneBidder()
    def plusOneBidder = new OpponentPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(medianBidder, plusOneBidder, quantity, cash, iterations)

    then:
    statistics[medianBidder].victories > statistics[plusOneBidder].victories

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
  def 'awesome bot vs plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def awesomeBidder = new AwesomeBidder()
    def plusOneBidder = new OpponentPlusOneBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, plusOneBidder, quantity, cash, iterations)

    then:
    statistics[awesomeBidder].victories > statistics[plusOneBidder].victories

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
    1000000  | 1000000 | 100 // loose
  }

}
