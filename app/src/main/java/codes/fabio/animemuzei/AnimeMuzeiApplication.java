package codes.fabio.animemuzei;

import android.app.Application;

public class AnimeMuzeiApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    TimberInitializer.init();
  }
}
