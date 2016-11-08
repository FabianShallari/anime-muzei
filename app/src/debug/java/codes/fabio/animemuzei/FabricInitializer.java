package codes.fabio.animemuzei;

import android.content.Context;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton class FabricInitializer {

  private final Context context;

  @Inject FabricInitializer(Context context) {
    this.context = context;
  }

  void init() {
    // no-op
  }
}
