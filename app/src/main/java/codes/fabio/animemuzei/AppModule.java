package codes.fabio.animemuzei;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module class AppModule {

  private final Context context;

  AppModule(Context context) {
    this.context = context.getApplicationContext();
  }

  @Provides @Singleton Context provideApplicationContext() {
    return context;
  }

  @Provides @Singleton SharedPreferences provideSharedPreferences(Context context) {
    return context.getSharedPreferences("prefs-animemuzei", Context.MODE_PRIVATE);
  }

  @Provides @Singleton LayoutInflater provideLayoutInflater(Context context) {
    return LayoutInflater.from(context);
  }
}