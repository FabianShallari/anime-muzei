package codes.fabio.animemuzei.imgur;

import android.content.Intent;
import android.net.Uri;
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

  static Intent viewIntent(String link) {
    return new Intent(Intent.ACTION_VIEW, Uri.parse(link));
  }
}
