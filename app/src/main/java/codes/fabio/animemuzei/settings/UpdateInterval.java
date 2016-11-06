package codes.fabio.animemuzei.settings;

import com.google.auto.value.AutoValue;
import java.util.concurrent.TimeUnit;

@AutoValue abstract class UpdateInterval {

  abstract int amount();

  abstract TimeUnit timeUnit();

  static UpdateInterval create(int amount, TimeUnit timeUnit) {
    return new AutoValue_UpdateInterval(amount, timeUnit);
  }
}
