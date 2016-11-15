package codes.fabio.animemuzei;

import android.util.Log;
import com.crashlytics.android.Crashlytics;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

@Singleton class TimberInitializer {

  private final CrashlyticsTree crashlyticsTree;

  @Inject TimberInitializer(CrashlyticsTree crashlyticsTree) {
    this.crashlyticsTree = crashlyticsTree;
  }

  void init() {
    Timber.plant(crashlyticsTree);
  }

  @Singleton static class CrashlyticsTree extends Timber.Tree {

    private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
    private static final String CRASHLYTICS_KEY_TAG = "tag";
    private static final String CRASHLYTICS_KEY_MESSAGE = "message";

    @Inject CrashlyticsTree() {
    }

    @Override protected boolean isLoggable(String tag, int priority) {
      return priority >= Log.WARN;
    }

    @Override protected void log(int priority, String tag, String message, Throwable t) {
      Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
      Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
      Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

      if (t != null) {
        Crashlytics.logException(t);
      } else {
        Crashlytics.logException(new RuntimeException(message));
      }
    }
  }

}
