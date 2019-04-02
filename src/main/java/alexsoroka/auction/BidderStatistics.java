package alexsoroka.auction;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BidderStatistics {
  private int victories = 0;
  private int losses = 0;
  private int draws = 0;

  public BidderStatistics addVictory() {
    this.victories++;
    return this;
  }

  public BidderStatistics addLoss() {
    this.losses++;
    return this;
  }

  public BidderStatistics addDraw() {
    this.draws++;
    return this;
  }

}
