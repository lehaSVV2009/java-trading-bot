package alexsoroka.bots;

import alexsoroka.common.BidResult;
import alexsoroka.common.WinFunctions;

import java.util.Scanner;

public class ConsoleBidder extends AbstractBidder {

  /**
   * Console input reader
   */
  private final Scanner scanner;

  /**
   * Current number of products bought by bidder.
   */
  private int ownPurchasesQuantity;

  /**
   * Current number of products bought by opponent.
   */
  private int opponentPurchasesQuantity;

  public ConsoleBidder(Scanner scanner) {
    this.scanner = scanner;
  }

  @Override
  protected void afterInit(int quantity, int cash) {
    this.ownPurchasesQuantity = 0;
    this.opponentPurchasesQuantity = 0;
  }

  @Override
  public int placeBid() {
    System.out.println("Your cash - " + ownCash +
        ". Opponent cash - " + opponentCash +
        ". Your quantity - " + ownPurchasesQuantity +
        ". Opponent quantity - " + opponentPurchasesQuantity);
    System.out.println("Remaining products quantity - " + (initialQuantity - ownPurchasesQuantity - opponentPurchasesQuantity));
    System.out.println("Place bid (positive, less than cash):");
    int bid = scanner.nextInt();
    while (bid < 0 || bid > ownCash) {
      System.out.println("Bid " + bid + " is wrong!");
      System.out.println("Place bid (positive, less than your cash):");
      bid = scanner.nextInt();
    }
    return bid;
  }

  @Override
  protected void afterBids(int own, int other) {
    System.out.println("Me" + own + " - " + other + " Opponent");

    BidResult bidResult = WinFunctions.findBidResult(own, other);
    switch (bidResult) {
      case PLAYER_1_WIN: {
        ownPurchasesQuantity += 2;
        break;
      }
      case PLAYER_2_WIN: {
        opponentPurchasesQuantity += 2;
        break;
      }
      default: {
        ownPurchasesQuantity++;
        opponentPurchasesQuantity++;
        break;
      }
    }
  }
}
