# java-trading-bot
Simple trading bot algorithms for first price sealed bid auction ([FPSBA](https://en.wikipedia.org/wiki/First-price_sealed-bid_auction)).

## Best algorithm strategy

The algorithm is located [here](/src/main/java/alexsoroka/bots/AwesomeBidder.java).

### How it works?

In general, algorithm works as a previous previous winner plus one or two with periodical small bids to not waste all money.


There are many [competition tests](/src/test/groovy/alexsoroka/competitions), and this algorithm shows best results in comparison with others.


First of all, it checks if computation is needed (e.g. no need to run algorithm, if there is no cash).
Then it checks some common scenarios when algorithm can win opponent without any random logic. (e.g. when there are only 2 quantities, or when there are 3 steps to win and own cash is 3 times greater than opponent's one).
If the bid is first, it will be small like 1 or 2 cause in general algorithm is agressive and it usually places large bids.
If the quantity of products is very small (less than 10) it will calculate median and will use median plus two cause after some observations I found that median algorithm is good in small quantities (it never lose to random).
Then it checks previous 2, 3 or 4 rounds. If all of them were too big (greater than initial average mean) algorithm will place small bid.
And finally place bid that 1 or 2 greater than previous round winner placed.
If any bid is greater than cash, random bid with limit of cash will be placed.

P.S. Of course, the algorithm looses sometimes in some cases.
P.S.2. Next best algorithm is [winner plus one or two bidder](/src/main/java/alexsoroka/bots/WinnerPlusOneOrTwoBidder.java) with its results [here](/src/test/groovy/alexsoroka/competitions/VersusWinnerPlusOneOrTwoBidderCompetitions.groovy).

### Potential improvements

* Detect more basic algorithms and use algorithms that win these basic algorithms.
* Detect opponent strategy (aggressive, patient, random) and use algorithms that win this strategy in most cases.
* Try to use genetic algorithm.
* Train neural network with own observations.

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

## Algorithms

* `ZeroOnlyBidder`
* `RandomBidder`
* `OpponentPlusOneBidder`
* `WinnerPlusOneOrTwoBidder`
* `AveragePlusOneBidder`
* `MedianPlusOneBidder`

## Future algorithms

* `NeuralNetworkBidder`
* `CurveFitBidder`
* `SequencePredictionBidder`

