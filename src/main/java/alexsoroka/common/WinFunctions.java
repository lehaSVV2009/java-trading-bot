package alexsoroka.common;

public class WinFunctions {
  /**
   * Compare bids on who win or lose
   *
   * @param own   bid of first player
   * @param other bid of second player
   * @return `1` - if own wins, `-1` - if own looses, `0` - if draw
   */
  public static int compareBids(int own, int other) {
    return Integer.compare(own, other);
  }

  /**
   * Find greater bid
   *
   * @param own   bid of first player
   * @param other bid of second player
   * @return greater bid
   */
  public static int findWinnerBid(int own, int other) {
    return compareBids(own, other) > 0 ? own : other;
  }

  /**
   * Get bid result from bids
   *
   * @param playerOneBid bid of first player
   * @param playerTwoBid bid of second player
   * @return PLAYER_1_WIN if playerOneBid is greater, PLAYER_2_WIN if playerTwoBid is greater, DRAW if equal
   */
  public static BidResult findBidResult(int playerOneBid, int playerTwoBid) {
    int comparison = WinFunctions.compareBids(playerOneBid, playerTwoBid);
    return comparison > 0 ? BidResult.PLAYER_1_WIN
        : comparison < 0 ? BidResult.PLAYER_2_WIN
        : BidResult.DRAW;
  }
}
