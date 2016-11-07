package codes.fabio.animemuzei;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

public class AnimeMuzeiApplication extends Application {

  private AppComponent appComponent;

  @Inject TimberInitializer timberInitializer;

  @Override public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics(), new Answers());
    injectDependencies();
    timberInitializer.init();
  }

  private void injectDependencies() {
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();

    appComponent.inject(this);
  }

  public static AppComponent getApplicationComponent(Context context) {
    return ((AnimeMuzeiApplication) context.getApplicationContext()).appComponent;
  }
}
