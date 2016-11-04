package codes.fabio.animemuzei;

import codes.fabio.animemuzei.imgur.ImgurImageRemoteDataSource;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class AnimeMuzeiRemoteSource extends RemoteMuzeiArtSource {

  private static final String SOURCE_NAME = "AnimeMuzeiArtSource";

  @Inject ImgurImageRemoteDataSource imgurImageRemoteDataSource;

  public AnimeMuzeiRemoteSource() {
    super(SOURCE_NAME);
  }

  @Override public void onCreate() {
    super.onCreate();
    DaggerAnimeMuzeiComponent.builder().build().inject(this);
  }

  @Override protected void onTryUpdate(int reason) throws RetryException {

    try {
      publishArtwork(imgurImageRemoteDataSource.nextImage().toBlocking().first());
    } catch (Exception e) {
      throw new RetryException(e);
    }

    scheduleUpdate(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));
  }
}
