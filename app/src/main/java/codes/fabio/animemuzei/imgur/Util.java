package codes.fabio.animemuzei.imgur;

import java.util.List;
import java.util.Random;

/**
 * Created by fabian on 11/4/16.
 */

class Util {

  private static Random random = new Random();

  private Util() {
    //no instance
  }

  static <T> T randomFromList(List<T> list) {
    random.setSeed(System.currentTimeMillis());
    return list.get(random.nextInt(list.size()));
  }
}
