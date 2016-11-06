package codes.fabio.animemuzei.settings;

import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

@Module class SettingsModule {

  @Provides @Singleton List<UpdateInterval> provideUpdateIntervalOptions() {
    return asList(UpdateInterval.create(5, MINUTES), UpdateInterval.create(10, MINUTES),
        UpdateInterval.create(30, MINUTES), UpdateInterval.create(1, HOURS),
        UpdateInterval.create(3, HOURS), UpdateInterval.create(6, HOURS),
        UpdateInterval.create(1, DAYS));
  }

}
