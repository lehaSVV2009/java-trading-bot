package alexsoroka.bots

import static alexsoroka.auction.AuctionsRunner.runTwoBiddersAuctions

import spock.lang.Specification
import spock.lang.Unroll

class BotsComparisonSpec extends Specification {

  //
  // ZERO ONLY BOT COMPARISON
  //

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

  //
  // RANDOM BOT COMPARISON
  //

  @Unroll
  def 'plusOne bot should win random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
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
  def 'winnerPlusOne bot should win random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def winnerPlusOneBidder = new WinnerPlusOneBidder()
    def randomBidder = new RandomBidder()

    when:
    def statistics = runTwoBiddersAuctions(winnerPlusOneBidder, randomBidder, quantity, cash, iterations)

    then:
    statistics[winnerPlusOneBidder].victories > statistics[randomBidder].victories

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
  def 'average bot should win random bid bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def averageBidder = new AveragePlusOneBidder()
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


  //
  // PLUS ONE BOT COMPARISON
  //

  @Unroll
  def 'plusTwo bot should win plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
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
  def 'average bot should win plusOne bot when quantity=#quantity, cash=#cash, iterations=#iterations'() {
    given:
    def averageBidder = new AveragePlusOneBidder()
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

}
