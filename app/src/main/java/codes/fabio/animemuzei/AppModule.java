package codes.fabio.animemuzei;

import android.content.Context;
import android.content.SharedPreferences;
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

  @Provides SharedPreferences provideSharedPreferences(Context context) {
    return context.getSharedPreferences("prefs-animemuzei", Context.MODE_PRIVATE);
  }
}