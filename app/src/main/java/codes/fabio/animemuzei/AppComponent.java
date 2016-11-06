package codes.fabio.animemuzei;

import codes.fabio.animemuzei.imgur.ImgurModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    ImgurModule.class, AppModule.class, RemoteServiceModule.class
})
interface AppComponent {

  void inject(AnimeMuzeiApplication animeMuzeiApplication);

  void inject(RemoteSourceService remoteSourceService);

}
