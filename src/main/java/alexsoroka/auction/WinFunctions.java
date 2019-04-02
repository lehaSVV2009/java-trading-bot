package alexsoroka.auction;

public class WinFunctions {
  public static int compareBids(int own, int other) {
    return Integer.compare(own, other);
  }

  public static int findWinnerBid(int own, int other) {
    return compareBids(own, other) > 0 ? own : other;
  }
}
