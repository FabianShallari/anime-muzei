package codes.fabio.animemuzei.settings;

import android.support.annotation.PluralsRes;
import codes.fabio.animemuzei.R;
import com.google.auto.value.AutoValue;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

@AutoValue abstract class UpdateInterval {

  private static final HashMap<TimeUnit, Integer> timeUnitQuantityStrings = new HashMap<>();

  static {
    timeUnitQuantityStrings.put(MINUTES, R.plurals.minutes);
    timeUnitQuantityStrings.put(HOURS, R.plurals.hours);
    timeUnitQuantityStrings.put(DAYS, R.plurals.days);
  }

  abstract int amount();

  abstract TimeUnit timeUnit();

  static UpdateInterval create(int amount, TimeUnit timeUnit) {
    return new AutoValue_UpdateInterval(amount, timeUnit);
  }

  boolean equalsMillis(long millis) {
    return millis == timeUnit().toMillis(amount());
  }

  @PluralsRes int quantityStringRes() {
    if (!timeUnitQuantityStrings.containsKey(timeUnit())) {
      throw new IllegalArgumentException("No Plurals declared for TimeUnit: " + timeUnit());
    }
    return timeUnitQuantityStrings.get(timeUnit());
  }

  long toMillis() {
    return timeUnit().toMillis(amount());
  }
}
