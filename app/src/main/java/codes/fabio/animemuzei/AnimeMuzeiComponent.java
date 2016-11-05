package codes.fabio.animemuzei;

import codes.fabio.animemuzei.imgur.ImgurModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { ImgurModule.class, AnimeMuzeiModule.class })
interface AnimeMuzeiComponent {
  void inject(AnimeMuzeiRemoteSource animeMuzeiRemoteSource);
}
