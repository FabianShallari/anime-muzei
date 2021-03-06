package codes.fabio.animemuzei;

import android.app.Application;
import android.content.Context;
import javax.inject.Inject;

public class AnimeMuzeiApplication extends Application {

  private AppComponent appComponent;

  @Inject TimberInitializer timberInitializer;
  @Inject FabricInitializer fabricInitializer;

  @Override public void onCreate() {
    super.onCreate();

    injectDependencies();

    fabricInitializer.init();
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
