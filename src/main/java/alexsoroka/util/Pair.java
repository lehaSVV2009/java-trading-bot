package alexsoroka.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple tuple with 2 fields
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Pair<L, R> {
  private L left;
  private R right;
}
