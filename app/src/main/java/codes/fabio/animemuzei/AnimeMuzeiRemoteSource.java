package codes.fabio.animemuzei;

import codes.fabio.animemuzei.imgur.ImgurImageRemoteDataSource;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import timber.log.Timber;

import static codes.fabio.animemuzei.Util.shareImageIntent;

public class AnimeMuzeiRemoteSource extends RemoteMuzeiArtSource {

  private static final String SOURCE_NAME = "AnimeMuzeiArtSource";
  static final int CUSTOM_COMMAND_ID_SHARE_ARTWORK = MAX_CUSTOM_COMMAND_ID - 1;

  @Inject ImgurImageRemoteDataSource imgurImageRemoteDataSource;
  @Inject List<UserCommand> userCommands;

  public AnimeMuzeiRemoteSource() {
    super(SOURCE_NAME);
  }

  @Override public void onCreate() {
    super.onCreate();
    injectDependencies();
    setUserCommands(userCommands);
  }

  private void injectDependencies() {
    DaggerAnimeMuzeiComponent.builder()
        .animeMuzeiModule(new AnimeMuzeiModule(getResources()))
        .build()
        .inject(this);
  }

  @Override protected void onTryUpdate(int reason) throws RetryException {
    try {
      Artwork artwork = imgurImageRemoteDataSource.nextImage().toBlocking().first();
      publishArtwork(artwork);
      Timber.d("published Artwork from Uri: %s", artwork.getViewIntent().getData());
    } catch (Throwable e) {
      Timber.e(e, "onTryUpdate failed. Throwing RetryException");
      throw new RetryException();
    }

    scheduleUpdate(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));
  }

  @Override protected void onCustomCommand(int id) {
    switch (id) {
      case CUSTOM_COMMAND_ID_SHARE_ARTWORK:
        startActivity(shareImageIntent(getResources().getString(R.string.share_artwork_message,
            getCurrentArtwork().getImageUri())));
        break;
      default:
        super.onCustomCommand(id);
    }
  }
}
