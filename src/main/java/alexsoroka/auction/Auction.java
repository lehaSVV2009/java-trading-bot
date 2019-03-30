package alexsoroka.auction;

import alexsoroka.bots.Bidder;

import java.util.Map;

/**
 * Auction actions
 */
public interface Auction {

  /**
   * Register bidder to a game
   *
   * @param bidder player to register
   * @param cash   amount of bidder money
   */
  void register(Bidder bidder, int cash);

  /**
   * Play bets with all products
   *
   * @return bidder <-> bidder purchases amount
   */
  Map<Bidder, Integer> run();
}
