package alexsoroka.auction;

public class WinFunctions {
  public static int compareBids(int own, int other) {
    return Integer.compare(own, other);
  }

  public static int findWinnerBid(int own, int other) {
    return compareBids(own, other) > 0 ? own : other;
  }

  public static BidResult findBidResult(int playerOneBid, int playerTwoBid) {
    int comparison = WinFunctions.compareBids(playerOneBid, playerTwoBid);
    return comparison > 0 ? BidResult.PLAYER_1_WIN
        : comparison < 0 ? BidResult.PLAYER_2_WIN
        : BidResult.DRAW;
  }
}
