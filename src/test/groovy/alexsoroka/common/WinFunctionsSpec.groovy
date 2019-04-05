package alexsoroka.common

import static alexsoroka.common.WinFunctions.compareBids
import static alexsoroka.common.WinFunctions.findBidResult
import static alexsoroka.common.WinFunctions.findWinnerBid

import spock.lang.Specification
import spock.lang.Unroll

class WinFunctionsSpec extends Specification {

  @Unroll
  def 'compareBids should return result=#result when own=#own and other=#other'() {
    when:
    int comparison = compareBids(own, other)

    then:
    comparison == result

    where:
    own | other || result
    5   | 3     || 1
    3   | 5     || -1
    2   | 2     || 0
    0   | 0     || 0
  }

  @Unroll
  def 'findWinnerBid should return result=#result when own=#own and other=#other'() {
    when:
    int winnerBid = findWinnerBid(own, other)

    then:
    winnerBid == result

    where:
    own | other || result
    5   | 3     || 5
    3   | 5     || 5
    2   | 2     || 2
    0   | 0     || 0
  }

  @Unroll
  def 'findBidResult should return result=#result when playerOneBid=#playerOneBid and playerTwoBid=#playerTwoBid'() {
    when:
    BidResult bidResult = findBidResult(playerOneBid, playerTwoBid)

    then:
    bidResult == result

    where:
    playerOneBid | playerTwoBid || result
    5            | 3            || BidResult.PLAYER_1_WIN
    3            | 5            || BidResult.PLAYER_2_WIN
    2            | 2            || BidResult.DRAW
    0            | 0            || BidResult.DRAW
  }

}
