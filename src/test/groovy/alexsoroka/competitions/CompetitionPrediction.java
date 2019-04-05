package alexsoroka.competitions;

import alexsoroka.auction.BidderStatistics;

public enum CompetitionPrediction {
  WIN, LOOSE, WIN_OR_LOOSE;

  public static boolean assertBidderStatistics(CompetitionPrediction prediction, BidderStatistics first, BidderStatistics second) {
    switch (prediction) {
      case WIN:
        return first.getVictories() > second.getVictories();
      case LOOSE:
        return first.getVictories() < second.getVictories();
      default:
        return true;
    }
  }
}
