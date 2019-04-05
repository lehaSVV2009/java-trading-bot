package alexsoroka.auction;

import alexsoroka.util.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Bidder achievements after auction
 */
@Getter
@ToString
@AllArgsConstructor(staticName = "of")
public class BidderAuctionResult implements Comparable<BidderAuctionResult> {
  /**
   * Number of products won by bidder
   */
  private int purchasesQuantity;

  /**
   * Bidder's cash after auction
   */
  private int remainingCash;

  /**
   * @param amount money to deduct
   */
  public void deductCash(int amount) {
    remainingCash -= amount;
  }

  /**
   * @param quantity number of products to add
   */
  public void addPurchase(int quantity) {
    this.purchasesQuantity += quantity;
  }

  /**
   * Compare this result with another
   *
   * @param other object to compare
   * @return `1` - if this result is better. `-1` - if this result is worse. `0` - if results are equal.
   */
  @Override
  public int compareTo(BidderAuctionResult other) {
    Assert.nonNull(other, "Comparable object must be not null");

    int otherPurchasesQuantity = other.getPurchasesQuantity();
    if (purchasesQuantity > otherPurchasesQuantity) {
      return 1;
    }
    if (purchasesQuantity < otherPurchasesQuantity) {
      return -1;
    }

    int otherRemainingCash = other.getRemainingCash();
    return Integer.compare(remainingCash, otherRemainingCash);
  }
}
