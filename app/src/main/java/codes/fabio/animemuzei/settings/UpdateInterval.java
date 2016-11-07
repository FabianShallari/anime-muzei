package codes.fabio.animemuzei.settings;

import com.google.auto.value.AutoValue;
import java.util.concurrent.TimeUnit;

@AutoValue public abstract class UpdateInterval {

  public abstract int amount();

  public abstract TimeUnit timeUnit();

  static UpdateInterval create(int amount, TimeUnit timeUnit) {
    return new AutoValue_UpdateInterval(amount, timeUnit);
  }

  public boolean equalsMillis(long millis) {
    return millis == timeUnit().toMillis(amount());
  }

  public long toMillis() {
    return timeUnit().toMillis(amount());
  }
}
