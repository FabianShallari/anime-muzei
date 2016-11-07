package codes.fabio.animemuzei;

import codes.fabio.animemuzei.imgur.ImgurModule;
import codes.fabio.animemuzei.remoteservice.RemoteServiceModule;
import codes.fabio.animemuzei.remoteservice.RemoteSourceService;
import codes.fabio.animemuzei.settings.SettingsActivity;
import codes.fabio.animemuzei.settings.SettingsModule;
import dagger.Component;
import javax.inject.Singleton;

@SuppressWarnings("WeakerAccess") @Singleton @Component(modules = {
    ImgurModule.class, AppModule.class, RemoteServiceModule.class, SettingsModule.class
}) public interface AppComponent {

  void inject(AnimeMuzeiApplication animeMuzeiApplication);

  void inject(RemoteSourceService remoteSourceService);

  void inject(SettingsActivity settingsActivity);
}
