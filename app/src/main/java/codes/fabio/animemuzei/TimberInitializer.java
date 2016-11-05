package codes.fabio.animemuzei;

import timber.log.Timber;

class TimberInitializer {

  static void init() {
    if (BuildConfig.DEBUG) {
      initDebug();
    } else {
      initRelease();
    }
  }

  private static void initDebug() {
    Timber.plant(new Timber.DebugTree() {
      @Override protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element) + ":" + element.getLineNumber();
      }
    });
  }

  private static void initRelease() {
    // Todo: plant Crashlytics Tree
  }


}
