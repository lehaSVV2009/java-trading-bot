package alexsoroka.competitions

import static alexsoroka.auction.Competitions.runTwoBiddersAuctions

import alexsoroka.bots.HistoryMeanPlusOneBidder
import alexsoroka.bots.AwesomeBidder
import alexsoroka.bots.MedianPlusOneBidder
import alexsoroka.bots.OpponentPlusOneBidder
import alexsoroka.bots.OpponentPlusTwoBidder
import alexsoroka.bots.RandomBidder
import alexsoroka.bots.WinnerMedianPlusOneBidder
import alexsoroka.bots.WinnerPlusOneOrTwoBidder
import spock.lang.Specification
import spock.lang.Unroll

class VersusRandomBidderCompetitions extends Specification {

  @Unroll
  def 'plusOne bot vs random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneBidder = new OpponentPlusOneBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[plusOneBidder].victories > statistics[randomBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // can loose
    10       | 1000    | 100 // can loose
    10       | 1000000 | 100 // can loose
    1000     | 10      | 100
    1000     | 1000    | 100
    1000     | 1000000 | 100
    1000000  | 10      | 100
    1000000  | 1000    | 100
    1000000  | 1000000 | 100
  }

  @Unroll
  def 'plusOneOrTwo bot vs random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def plusOneOrTwoBidder = new OpponentPlusTwoBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(plusOneOrTwoBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[plusOneOrTwoBidder].victories > statistics[randomBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // can loose
    10       | 1000    | 100 // can loose
    10       | 1000000 | 100 // can loose
    1000     | 10      | 100
    1000     | 1000    | 100
    1000     | 1000000 | 100
    1000000  | 10      | 100
    1000000  | 1000    | 100
    1000000  | 1000000 | 100
  }

  @Unroll
  def 'winnerPlusOneOrTwo bot vs random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerPlusOneOrTwoBidder = new WinnerPlusOneOrTwoBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(winnerPlusOneOrTwoBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[winnerPlusOneOrTwoBidder].victories > statistics[randomBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // can loose
    10       | 1000    | 100 // can loose
    10       | 1000000 | 100 // can loose
    1000     | 10      | 100 // can loose
    1000     | 1000    | 100
    1000     | 1000000 | 100
    1000000  | 10      | 100
    1000000  | 1000    | 100
    1000000  | 1000000 | 100
  }

  @Unroll
  def 'history mean bot vs random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def averageBidder = new HistoryMeanPlusOneBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(averageBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[averageBidder].victories > statistics[randomBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // can loose
    10       | 1000    | 100 // can loose
    10       | 1000000 | 100
    1000     | 10      | 100
    1000     | 1000    | 100
    1000     | 1000000 | 100 // loose
    1000000  | 10      | 100
    1000000  | 1000    | 100
    1000000  | 1000000 | 100 // loose
  }

  @Unroll
  def 'median bot vs random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def medianBidder = new MedianPlusOneBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(medianBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[medianBidder].victories > statistics[randomBidder].victories

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
  def 'winner median bot vs random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerMedianBidder = new WinnerMedianPlusOneBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(winnerMedianBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[winnerMedianBidder].victories > statistics[randomBidder].victories

    where:
    quantity | cash    | iterations
    10       | 10      | 100 // can loose
    10       | 1000    | 100 // can loose
    10       | 1000000 | 100 // can loose
    1000     | 10      | 100 // can loose
    1000     | 1000    | 100
    1000     | 1000000 | 100
    1000000  | 10      | 100 // can loose
    1000000  | 1000    | 100
    1000000  | 1000000 | 100
  }

  @Unroll
  def 'awesome bot vs random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def awesomeBidder = new AwesomeBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(awesomeBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[awesomeBidder].victories > statistics[randomBidder].victories

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
