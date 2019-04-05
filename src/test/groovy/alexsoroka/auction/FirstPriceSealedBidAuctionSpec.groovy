package alexsoroka.auction

import alexsoroka.bots.AwesomeBidder
import alexsoroka.bots.Bidder
import alexsoroka.bots.RandomBidder
import spock.lang.Specification

class FirstPriceSealedBidAuctionSpec extends Specification {

  Bidder cleverBidder
  Bidder randomBidder

  def setup() {
    cleverBidder = new AwesomeBidder()
    randomBidder = new RandomBidder()
  }

  def 'run should return 0 purchases for 0 auction products quantity'() {
    given:
    int productsQuantity = 0
    int cash = 0
    def auction = new FirstPriceSealedBidAuction(cleverBidder, randomBidder, productsQuantity, cash)

    when:
    def bidderPurchaseMap = auction.run()

    then:
    bidderPurchaseMap[cleverBidder].purchasesQuantity == 0
    bidderPurchaseMap[randomBidder].purchasesQuantity == 0
  }

  def 'run should return some purchases for 30 auction products quantity'() {
    given:
    int productsQuantity = 30
    int cash = 30
    def auction = new FirstPriceSealedBidAuction(cleverBidder, randomBidder, productsQuantity, cash)

    when:
    def bidderPurchaseMap = auction.run()

    then:
    bidderPurchaseMap[cleverBidder].purchasesQuantity > 0 || bidderPurchaseMap[randomBidder].purchasesQuantity > 0
  }
}
