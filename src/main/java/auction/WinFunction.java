package auction;

import java.util.function.BiFunction;

// TODO probably try to use utils with static context
public class WinFunction implements BiFunction<Integer, Integer, WinFunction.BidResult> {
  public enum BidResult {
    PLAYER_1_WIN,
    PLAYER_2_WIN,
    DRAW
  }

  @Override
  public BidResult apply(Integer own, Integer other) {
    return own > other ? BidResult.PLAYER_1_WIN
        : own < other ? BidResult.PLAYER_2_WIN
        : BidResult.DRAW;
  }
}
