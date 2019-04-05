# java-trading-bot
Simple trading bot algorithms for first price sealed bid auction ([FPSBA](https://en.wikipedia.org/wiki/First-price_sealed-bid_auction)).

## Best algorithm strategy

The algorithm is located [here](/src/main/java/alexsoroka/bots/AwesomeBidder.java).

There are many [competition tests](/src/test/groovy/alexsoroka/competitions), and this algorithm shows best results in comparison with others.

### How it works?

In general, algorithm works as a previous winner bid plus one or two with periodical small bids to not waste all money.


1. First of all, it checks if computation is needed (e.g. no need to run algorithm, if there is no cash).
2. Then it checks some common scenarios when algorithm can win opponent without any random logic. (e.g. when there are only 2 quantities, or when there are 3 steps to win and own cash is 3 times greater than opponent's one).
3. If the bid is first, it will be small bid like 1 or 2 cause in general algorithm is agressive and it usually places large bids.
4. If the quantity of products is very small (less than 10) it will calculate median and will use median plus two bid cause after some observations I found that median algorithm is good in small quantities (it never lose to random).
5. Then it checks previous 2, 3 or 4 rounds. If all of them were too big (greater than initial average mean) the algorithm will place small bid.
6. And finally if all the checks are passed, the algorithm will place a previous round winner bid plus one or two.
7. If any bid is greater than cash, random bid with cash limit will be placed.

P.S. Of course, the algorithm looses sometimes in some cases.

P.S.2. Next best algorithm is [winner plus one or two bidder](/src/main/java/alexsoroka/bots/WinnerPlusOneOrTwoBidder.java) with its competitions [here](/src/test/groovy/alexsoroka/competitions/VersusWinnerPlusOneOrTwoBidderCompetitions.groovy).

### Potential improvements

* Detect more basic algorithms and use algorithms that win these basic algorithms.
* Detect opponent strategy (aggressive, patient, random) and use algorithms that win this strategy in most cases.
* Try to use genetic algorithm.
* Train neural network with own observations.

## Algorithms

All bots are located [here](/src/main/java/alexsoroka/bots).

* [AwesomeBidder](/src/main/java/alexsoroka/bots/AwesomeBidder.java) - complex algorithm.
* [ZeroOnlyBidder](/src/main/java/alexsoroka/bots/ZeroOnlyBidder.java) - always bids 0.
* [RandomBidder](/src/main/java/alexsoroka/bots/RandomBidder.java) - always bids random value within cash.
* [OpponentPlusOneBidder](/src/main/java/alexsoroka/bots/OpponentPlusOneBidder.java) - bids previous opponent's bid plus one.
* [OpponentPlusOneOrTwoBidder](/src/main/java/alexsoroka/bots/OpponentPlusOneOrTwoBidder.java) - bids previous opponent's bid plus one or two.
* [WinnerPlusOneOrTwoBidder](/src/main/java/alexsoroka/bots/WinnerPlusOneOrTwoBidder.java) - bids previous winner's bid plus one or two.
* [HistoryMeanPlusOneBidder](/src/main/java/alexsoroka/bots/HistoryMeanPlusOneBidder.java) - bids average plus one bid that calculated from all previous bids.
* [MedianPlusOneBidder](/src/main/java/alexsoroka/bots/MedianPlusOneBidder.java) - bids median plus one bid that calculated from 100 previous bids.
* [WinnerMedianPlusOneBidder](/src/main/java/alexsoroka/bots/MedianPlusOneBidder.java) - bids median plus one bid that calculated from 100 previous winner bids.

## Future algorithms

* `NeuralNetworkBidder`
* `CurveFitBidder`
* `SequencePredictionBidder`
* `GeneticBidder`

## Rules

A product x QU (quantity units) will be auctioned under 2 parties. The parties have each y MU
(monetary units) for auction. They offer then simultaneously an arbitrary number of its MU on the
first 2 QU of the product. After that, the bids will be visible to both. The 2 QU of the product is
awarded to who has offered the most MU; if both bid the same, then both get 1 QU. Both bidders
must pay their amount - including the defeated. A bid of 0 MU is allowed. Bidding on each 2 QU is
repeated until the supply of x QU is fully auctioned. Each bidder aims to get a larger amount than its
competitor.


In an auction wins the program that is able to get more QU than the other. With a tie, the program
that retains more MU wins. Write a program that can participate in such an auction and competes
with one of our programs.


The bidder interface:

```java
package auction;

/**
 * Represents a bidder for the action.
 */
public interface Bidder {

  /**
   * Initializes the bidder with the production quantity and the allowed cash limit.
   *
   * @param quantity
   * the quantity
   * @param cash
   * the cash limit
   */
  void init(int quantity, int cash);

  /**
   * Retrieves the next bid for the product, which may be zero.
   *
   * @return the next bid
   */
  int placeBid();

  /**
   * Shows the bids of the two bidders.
   *
   * @param own
   * the bid of this bidder
   * @param other
   * the bid of the other bidder
   */
  void bids(int own, int other);

}
```
