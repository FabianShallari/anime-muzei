package codes.fabio.animemuzei;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.PluralsRes;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class Util {

  private static final Random random = new Random();
  private static final HashMap<String, Typeface> typefaces = new HashMap<>();
  private static final HashMap<TimeUnit, Integer> timeUnitQuantityStrings = new HashMap<>();

  static {
    timeUnitQuantityStrings.put(MINUTES, R.plurals.minutes);
    timeUnitQuantityStrings.put(HOURS, R.plurals.hours);
    timeUnitQuantityStrings.put(DAYS, R.plurals.days);
  }

  private Util() {
    //no instance
  }

  public static Typeface getTypeFace(Context context, String name) {
    if (!typefaces.containsKey(name)) {
      typefaces.put(name, Typeface.createFromAsset(context.getAssets(), "fonts/" + name));
    }

    return typefaces.get(name);
  }

  public static <T> T randomFromList(List<T> list) {
    random.setSeed(System.currentTimeMillis());
    return list.get(random.nextInt(list.size()));
  }

  public static Intent viewIntent(String link) {
    return new Intent(Intent.ACTION_VIEW, Uri.parse(link));
  }

  static Intent shareImageIntent(String message) {
    Intent shareImageIntent = new Intent(Intent.ACTION_SEND);
    shareImageIntent.putExtra(Intent.EXTRA_TEXT, message);
    shareImageIntent.setType("text/plain");
    return Intent.createChooser(shareImageIntent, "Share Image Via")
        .addFlags(FLAG_ACTIVITY_NEW_TASK);
  }

  @PluralsRes public static int quantityStringForTimeUnit(TimeUnit timeUnit) {
    if (!timeUnitQuantityStrings.containsKey(timeUnit)) {
      throw new IllegalArgumentException("No Plurals declared for TimeUnit: " + timeUnit);
    }
    return timeUnitQuantityStrings.get(timeUnit);
  }
}
