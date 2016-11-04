package codes.fabio.animemuzei;

import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by fabian on 11/4/16.
 */

@Singleton @Component(modules = NetworkModule.class) public interface AnimeMuzeiComponent {
  void inject(AnimeMuzeiRemoteSource animeMuzeiRemoteSource);
}
