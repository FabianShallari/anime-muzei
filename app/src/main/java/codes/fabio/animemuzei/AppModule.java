package codes.fabio.animemuzei;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module class AppModule {

  private final Context context;

  AppModule(Context context) {
    this.context = context.getApplicationContext();
  }

  @Provides Context provideApplicationContext() {
    return context;
  }
}
