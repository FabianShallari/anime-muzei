package codes.fabio.animemuzei;

import android.content.SharedPreferences;
import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.concurrent.TimeUnit.DAYS;

@Singleton public class SharedPrefsHelper {

  private static final String PREF_NSFW_ENABLED = "nsfw_enabled";
  private static final String PREF_UPDATE_INTERVAL_MILLIS = "update_interval_millis";

  private final SharedPreferences sharedPreferences;

  @Inject SharedPrefsHelper(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  public boolean isNsfwEnabled() {
    return sharedPreferences.getBoolean(PREF_NSFW_ENABLED, false);
  }

  public void setNsfwEnabled(boolean enable) {
    sharedPreferences.edit().putBoolean(PREF_NSFW_ENABLED, enable).apply();
  }

  public long getUpdateIntervalMillis() {
    return sharedPreferences.getLong(PREF_UPDATE_INTERVAL_MILLIS, DAYS.toMillis(1));
  }

  public void setUpdateIntervalMillis(long updateIntervalMillis) {
    sharedPreferences.edit().putLong(PREF_UPDATE_INTERVAL_MILLIS, updateIntervalMillis).apply();
  }
}
