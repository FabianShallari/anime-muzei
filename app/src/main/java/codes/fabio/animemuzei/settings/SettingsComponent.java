package codes.fabio.animemuzei.settings;

import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = SettingsModule.class) interface SettingsComponent {

  void inject(SettingsActivity settingsActivity);
}
