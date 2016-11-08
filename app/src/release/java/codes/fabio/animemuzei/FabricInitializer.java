package codes.fabio.animemuzei;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton class FabricInitializer {

  private final Context context;

  @Inject FabricInitializer(Context context) {
    this.context = context;
  }

  void init() {
    Fabric.with(context, new Crashlytics(), new Answers());
  }
}
