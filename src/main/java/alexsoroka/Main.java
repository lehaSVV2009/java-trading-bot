package alexsoroka;

import alexsoroka.auction.Auction;
import alexsoroka.auction.BidderAuctionResult;
import alexsoroka.auction.FirstPriceSealedBidAuction;
import alexsoroka.bots.AwesomeBidder;
import alexsoroka.bots.Bidder;
import alexsoroka.bots.ConsoleBidder;

import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    System.out.println("Enter products quantity (positive, even):");
    Scanner in = new Scanner(System.in);
    int quantity = in.nextInt();

    System.out.println("Enter cash:");
    int cash = in.nextInt();

    Bidder bot = new AwesomeBidder();
    Bidder human = new ConsoleBidder(in);

    Auction auction = new FirstPriceSealedBidAuction(bot, human, quantity, cash);
    Map<Bidder, BidderAuctionResult> result = auction.run();

    BidderAuctionResult humanResult = result.get(human);
    BidderAuctionResult botResult = result.get(bot);
    int comparison = humanResult.compareTo(botResult);

    System.out.println(comparison > 0 ? "You WIN!" : comparison < 0 ? "Bot WIN!" : "DRAW!");
    System.out.println("Your results: " + result.get(human));
    System.out.println("Bot results: " + result.get(bot));
  }
}
