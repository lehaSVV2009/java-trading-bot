package alexsoroka.auction

import alexsoroka.bots.Bidder
import alexsoroka.bots.RandomBidder
import spock.lang.Specification

class TwoBiddersAuctionSpec extends Specification {
  Bidder cleverBidder
  Bidder randomBidder
  WinFunction winFunction

  def setup() {
    cleverBidder = new RandomBidder()
    randomBidder = new RandomBidder()
    winFunction = new WinFunction()
  }

  def 'run should return 0 purchases for 0 auction products quantity'() {
    given:
    def productsQuantity = 0
    def auction = new TwoBiddersAuction(productsQuantity, new WinFunction())
    auction.register(cleverBidder, 10)
    auction.register(randomBidder, 10)

    when:
    def bidderPurchaseMap = auction.run()

    then:
    bidderPurchaseMap[cleverBidder] == 0
    bidderPurchaseMap[randomBidder] == 0
  }

  def 'run should return some purchases for 30 auction products quantity'() {
    given:
    def productsQuantity = 30
    def auction = new TwoBiddersAuction(productsQuantity, new WinFunction())
    auction.register(cleverBidder, 10)
    auction.register(randomBidder, 10)

    when:
    def bidderPurchaseMap = auction.run()

    then:
    bidderPurchaseMap[cleverBidder] > 0 || bidderPurchaseMap[randomBidder] > 0
  }

}
