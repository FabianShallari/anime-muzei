package codes.fabio.animemuzei.remoteservice;

import codes.fabio.animemuzei.AppSettings;
import codes.fabio.animemuzei.imgur.ImgurImageRemoteDataSource;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import static java.lang.System.currentTimeMillis;

@Singleton class RemoteServiceDelegate {

  private AnimeMuzeiRemoteSourceService animeMuzeiRemoteSourceService;

  private final List<UserCommand> userCommands;
  private final AppSettings appSettings;
  private final ImgurImageRemoteDataSource imgurImageRemoteDataSource;

  @Inject RemoteServiceDelegate(List<UserCommand> userCommands, AppSettings appSettings,
      ImgurImageRemoteDataSource imgurImageRemoteDataSource) {

    this.userCommands = userCommands;
    this.appSettings = appSettings;
    this.imgurImageRemoteDataSource = imgurImageRemoteDataSource;
  }

  void attachService(AnimeMuzeiRemoteSourceService service) {
    this.animeMuzeiRemoteSourceService = service;
    getService().setCustomCommands(userCommands);
  }

  void detachService() {
    this.animeMuzeiRemoteSourceService = null;
  }

  void rescheduleOnly() {
    getService().setNextUpdate(currentTimeMillis() + appSettings.getUpdateIntervalMillis());
  }

  void tryUpdate(int reason) throws RemoteMuzeiArtSource.RetryException {
    try {
      Artwork artwork = imgurImageRemoteDataSource.nextImage().toBlocking().first();
      getService().setArtwork(artwork);
    } catch (Throwable e) {
      throw new RemoteMuzeiArtSource.RetryException();
    }

    getService().setNextUpdate(currentTimeMillis() + appSettings.getUpdateIntervalMillis());
  }

  AnimeMuzeiRemoteSourceService getService() {
    if (animeMuzeiRemoteSourceService == null) {
      throw new IllegalStateException("Service not attached yet or has already been detached");
    }

    return animeMuzeiRemoteSourceService;
  }
}
